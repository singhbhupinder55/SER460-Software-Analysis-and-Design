package edu.asu.sdc.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Announcement {
    private String announcementId;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    public Announcement(String announcementId, String title, String message, LocalDateTime createdAt) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

        return "Announcement ID: " + announcementId +
                "\nTitle: " + title +
                "\nMessage: " + message +
                "\nCreated At: " + createdAt.format(formatter);
    }
}