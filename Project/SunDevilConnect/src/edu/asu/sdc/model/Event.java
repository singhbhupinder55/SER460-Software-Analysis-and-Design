package edu.asu.sdc.model;

public class Event {
    private String eventId;
    private String title;
    private String description;
    private String dateTime;
    private String location;
    private String category;
    private boolean isPaid;
    private int popularityScore;
    private int capacity;
    private String status;

    public Event(String eventId, String title, String description, String dateTime,
                 String location, String category, boolean isPaid, int popularityScore,
                 int capacity, String status) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.category = category;
        this.isPaid = isPaid;
        this.popularityScore = popularityScore;
        this.capacity = capacity;
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public int getPopularityScore() {
        return popularityScore;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public void updateDetails(Event updatedEvent) {
        this.title = updatedEvent.title;
        this.description = updatedEvent.description;
        this.dateTime = updatedEvent.dateTime;
        this.location = updatedEvent.location;
        this.category = updatedEvent.category;
        this.isPaid = updatedEvent.isPaid;
        this.popularityScore = updatedEvent.popularityScore;
        this.capacity = updatedEvent.capacity;
        this.status = updatedEvent.status;
    }

    public void cancel() {
        this.status = "Cancelled";
    }

    public boolean isFull(int currentRegistrations) {
        return currentRegistrations >= capacity;
    }

    @Override
    public String toString() {
        return "Event ID: " + eventId +
                "\nTitle: " + title +
                "\nDescription: " + description +
                "\nDate/Time: " + dateTime +
                "\nLocation: " + location +
                "\nCategory: " + category +
                "\nPaid Event: " + isPaid +
                "\nPopularity Score: " + popularityScore +
                "\nCapacity: " + capacity +
                "\nStatus: " + status;
    }
}