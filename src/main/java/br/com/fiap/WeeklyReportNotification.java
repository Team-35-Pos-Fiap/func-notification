package br.com.fiap;

import java.util.List;

public class WeeklyReportNotification extends QueueNotification {

    private WeeklyReportPayload payload;

    public WeeklyReportNotification() {
        super();
    }

    public WeeklyReportPayload getPayload() {
        return payload;
    }

    public void setPayload(WeeklyReportPayload payload) {
        this.payload = payload;
    }

    public static class WeeklyReportPayload {

        private String courseId;
        private String courseTitle;
        private String teacherId;
        private String teacherName;
        private String teacherEmail;
        private String startDate;
        private String endDate;
        private int totalReviews;
        private double averageRating;
        private int urgentReviewsCount;
        private List<String> urgentReviewComments = List.of();

        public WeeklyReportPayload() {
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public void setCourseTitle(String courseTitle) {
            this.courseTitle = courseTitle;
        }

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherEmail() {
            return teacherEmail;
        }

        public void setTeacherEmail(String teacherEmail) {
            this.teacherEmail = teacherEmail;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getTotalReviews() {
            return totalReviews;
        }

        public void setTotalReviews(int totalReviews) {
            this.totalReviews = totalReviews;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }

        public int getUrgentReviewsCount() {
            return urgentReviewsCount;
        }

        public void setUrgentReviewsCount(int urgentReviewsCount) {
            this.urgentReviewsCount = urgentReviewsCount;
        }

        public List<String> getUrgentReviewComments() {
            return urgentReviewComments;
        }

        public void setUrgentReviewComments(List<String> urgentReviewComments) {
            this.urgentReviewComments = urgentReviewComments;
        }
    }

}
