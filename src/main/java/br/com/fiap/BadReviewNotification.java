package br.com.fiap;

public class BadReviewNotification extends QueueNotification {
    private BadReviewPayload payload;

    public BadReviewNotification() {
        super();
    }

    public BadReviewPayload getPayload() {
        return payload;
    }

    public void setPayload(BadReviewPayload payload) {
        this.payload = payload;
    }

    public static class BadReviewPayload {
        private int rating;
        private String reviewContent;

        public BadReviewPayload() {}

        public int getRating() { return rating; }
        public void setRating(int rating) { this.rating = rating; }

        public String getReviewContent() { return reviewContent; }
        public void setReviewContent(String reviewContent) { this.reviewContent = reviewContent; }
    }
}
