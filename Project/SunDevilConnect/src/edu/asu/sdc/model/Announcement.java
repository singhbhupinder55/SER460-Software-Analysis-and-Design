package edu.asu.sdc.model;

public class Announcement {
    private String announcementId;
    private String title;
    private String message;
    private String createdAt;

    public Announcement(String announcementId, String title, String message, String createdAt) {
        this.announcementId = announcementId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Announcement ID: " + announcementId +
                "\nTitle: " + title +
                "\nMessage: " + message +
                "\nCreated At: " + createdAt;
    }
}