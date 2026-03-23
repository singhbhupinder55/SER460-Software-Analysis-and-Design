package edu.asu.sdc.model;

public class EventRegistration {
    private String registrationId;
    private String registeredAt;
    private String status;

    public EventRegistration(String registrationId, String registeredAt, String status) {
        this.registrationId = registrationId;
        this.registeredAt = registeredAt;
        this.status = status;
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

    public void confirm() {
        status = "Confirmed";
    }

    public void cancel() {
        status = "Cancelled";
    }

    @Override
    public String toString() {
        return "Registration ID: " + registrationId +
                "\nRegistered At: " + registeredAt +
                "\nStatus: " + status;
    }
}