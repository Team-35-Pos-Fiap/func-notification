package br.com.fiap;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;

public class FuncNotifications {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @FunctionName("func-notifications")
    public void run(
            @QueueTrigger(name = "message", queueName = "queue-cursos", connection = "storagecursos_STORAGE") String message,
            final ExecutionContext context
    ) {
        context.getLogger().info(() -> "Raw message received: " + message);

        // try {
        //     NotificationMessage notification = objectMapper.readValue(message, NotificationMessage.class);
        //     if (notification == null || notification.payload == null) {
        //         context.getLogger().warning("Message or payload is null, skipping processing.");
        //         return; // prevents poison queue
        //     }
        //     context.getLogger().info(() -> "Parsed message type: " + notification.type);
        //     context.getLogger().info(() -> "Recipient: " + notification.recipientEmail);
        //     context.getLogger().info(() -> "Rating: " + notification.payload.rating);
        //     context.getLogger().info(() -> "Review: " + notification.payload.reviewContent);
        //     context.getLogger().info(() -> "Timestamp: " + notification.timestamp);
        // } catch (Exception e) {
        //     context.getLogger().severe(() -> "Failed to process message: " + e.getClass().getName() + " - " + e.getMessage());
        //     // Do not rethrow; this ensures the message is not sent to the poison queue
        // }
        try {
            EmailService emailService = new EmailService();

            JsonNode node = objectMapper.readTree(message);
            String type = node.get("type").asText();
            String recipientEmail = node.get("recipientEmail").asText();

            String subject;
            String body;

            switch (type) {
                case "BAD_REVIEW" -> {
                    BadReviewNotification badReviewNotification = objectMapper.readValue(message, BadReviewNotification.class);
                    subject = "Alert: Negative Review Received";
                    var payload = badReviewNotification.getPayload();
                    body = String.format(
                            "You received a negative review.\nRating: %d\nComment: %s",
                            payload.getRating(),
                            payload.getReviewContent()
                    );
                }
                case "WEEKLY_REPORT" -> {
                    WeeklyReportNotification weeklyReportNotification =objectMapper.readValue(message, WeeklyReportNotification.class);
                    subject = "Course Review Summary Available";

                    var payload = weeklyReportNotification.getPayload();

                    body = String.format(
                            "Course Review Summary\n\n"
                            + "Course: %s\n"
                            + "Instructor: %s\n"
                            + "Review Period: %s to %s\n\n"
                            + "Total Reviews: %d\n"
                            + "Average Rating: %.1f",
                            payload.getCourseTitle(),
                            payload.getTeacherName(),
                            payload.getStartDate(),
                            payload.getEndDate(),
                            payload.getTotalReviews(),
                            payload.getAverageRating()
                    );

                    if (payload.getUrgentReviewsCount() > 0) {
                        body += String.format(
                                "\n\nUrgent Reviews: %d\nComments:\n%s",
                                payload.getUrgentReviewsCount(),
                                String.join("\n", payload.getUrgentReviewComments())
                        );
                    }

                }
                default -> {
                    context.getLogger().warning(() -> "Unknown notification type: " + type);
                    return;
                }
            }

            emailService.sendEmail(subject, body, List.of(recipientEmail));

            context.getLogger().info("Email sent successfully");

        } catch (Exception e) {
            context.getLogger().severe(() -> "Error processing message: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
