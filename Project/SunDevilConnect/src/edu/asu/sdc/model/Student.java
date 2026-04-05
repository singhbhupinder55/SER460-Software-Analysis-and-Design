package edu.asu.sdc.model;

import java.time.LocalDate;

public class Student extends User {
    private String major;
    private int graduationYear;

    public Student(String userId, String name, String email, String status, String major, int graduationYear) {
        super(userId, name, email, status);
        this.major = major;
        this.graduationYear = graduationYear;
    }

    public String getMajor() {
        return major;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void searchEvents(String keyword) {
        System.out.println(getName() + " searched for events using keyword: " + keyword);
    }

    public void filterEvents(String criteria) {
        System.out.println(getName() + " filtered events by: " + criteria);
    }

    public void viewEventDetails(String eventId) {
        System.out.println(getName() + " is viewing details for event: " + eventId);
    }

    public EventRegistration registerForEvent(Event event) {
        System.out.println(getName() + " registered for event: " + event.getTitle());
        return new EventRegistration(
            "R-" + getUserId() + "-" + event.getEventId(),
            LocalDate.now().toString(),
            "Confirmed",
            getUserId(),
            event.getEventId()
        );
    }

    public void requestClubMembership(String clubId) {
        System.out.println(getName() + " requested membership for club: " + clubId);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nMajor: " + major +
                "\nGraduation Year: " + graduationYear;
    }
}
