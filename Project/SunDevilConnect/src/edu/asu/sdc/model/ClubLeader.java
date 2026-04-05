package edu.asu.sdc.model;

public class ClubLeader extends User {
    private String positionTitle;

    public ClubLeader(String userId, String name, String email, String status, String positionTitle) {
        super(userId, name, email, status);
        this.positionTitle = positionTitle;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public Event createEvent(Event event) {
        System.out.println(getName() + " created event: " + event.getTitle());
        return event;
    }

    public Event editEvent(Event event, Event updatedEvent) {
        event.updateDetails(updatedEvent);
        System.out.println(getName() + " edited event: " + event.getEventId());
        return event;
    }

    public void cancelEvent(Event event) {
        event.cancel();
        System.out.println(getName() + " cancelled event: " + event.getTitle());
    }

    public Announcement postAnnouncement(Announcement announcement) {
        System.out.println(getName() + " posted announcement: " + announcement.getTitle());
        return announcement;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nPosition Title: " + positionTitle;
    }
}