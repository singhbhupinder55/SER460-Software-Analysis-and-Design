package edu.asu.sdc.model;

public class EventRegistration {
    private String registrationId;
    private String registeredAt;
    private String status;
    private String studentId;
    private String eventId;

    public EventRegistration(String registrationId, String registeredAt, String status, String studentId, String eventId) {
        this.registrationId = registrationId;
        this.registeredAt = registeredAt;
        this.status = status;
        this.studentId = studentId;
        this.eventId = eventId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public String getStatus() {
        return status;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getEventId() {
        return eventId;
    }

    public void confirm() {
        status = "Confirmed";
    }

    public void cancel() {
        status = "Cancelled";
    }

    @Override
    public String toString() {
        return "Registration ID: " + registrationId +
                "\nStudent ID: " + studentId +
                "\nEvent ID: " + eventId +
                "\nRegistered At: " + registeredAt +
                "\nStatus: " + status;
    }
}