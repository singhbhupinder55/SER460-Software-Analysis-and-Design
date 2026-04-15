package edu.asu.sdc.model;

import java.time.LocalDate;

public class FlaggedContent {
    private String flagId;
    private String contentType;
    private String reason;
    private String status;
    private LocalDate flaggedAt;

    public FlaggedContent(String flagId, String contentType, String reason, String status, LocalDate flaggedAt) {
        this.flagId = flagId;
        this.contentType = contentType;
        this.reason = reason;
        this.status = status;
        this.flaggedAt = flaggedAt;
    }

    public String getFlagId() {
        return flagId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getFlaggedAt() {
        return flaggedAt;
    }

    public void resolve() {
        this.status = "Resolved";
    }

    public void dismiss() {
        this.status = "Dismissed";
    }

    @Override
    public String toString() {
        return "Flag ID: " + flagId +
                "\nContent Type: " + contentType +
                "\nReason: " + reason +
                "\nStatus: " + status +
                "\nFlagged At: " + flaggedAt;
    }
}