package edu.asu.sdc.main;
import edu.asu.sdc.controller.ClubController;
import edu.asu.sdc.controller.EventController;
import edu.asu.sdc.controller.RegistrationController;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.ClubLeader;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.EventRegistration;
import edu.asu.sdc.model.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventController eventController = new EventController();
        RegistrationController registrationController = new RegistrationController();
        ClubController clubController = new ClubController();

        Student student = new Student(
                "S101",
                "Bhupinder",
                "bsingh55@asu.edu",
                "Software Engineering",
                2026
        );

        ClubLeader leader = new ClubLeader(
                "L101",
                "Alex Johnson",
                "alex@asu.edu",
                "President"
        );

        Club club = new Club(
                "C101",
                "Coding Club",
                "A club for students interested in programming.",
                "Tech",
                "Approved"
        );

        Event event = new Event(
                "E101",
                "Coding Workshop",
                "Intro to Java programming",
                "2026-04-10 5:00 PM",
                "Engineering Building",
                "Tech",
                false,
                10,
                50,
                "Active"
        );

        Announcement announcement = new Announcement(
                "A101",
                "Welcome Message",
                "Welcome to the coding club!",
                "2026-04-01"
        );

        System.out.println("======================================");
        System.out.println("      SunDevil Connect Demo App       ");
        System.out.println("======================================\n");

        System.out.println("1. Setting up club...");
        clubController.addClub(club);
        System.out.println("Club added successfully.\n");

        System.out.println("2. Club leader creates an event...");
        leader.createEvent(event);
        eventController.createEvent(club, event);
        System.out.println("Event created and added to the club.\n");

        System.out.println("3. Club leader posts an announcement...");
        leader.postAnnouncement(announcement);
        clubController.postAnnouncement(club, announcement);
        System.out.println("Announcement posted successfully.\n");

        System.out.println("4. Student browses available events...");
        List<Event> events = eventController.browseEvents();
        for (Event e : events) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("5. Student registers for the event...");
        EventRegistration registration = registrationController.register(student, event);
        if (registration != null) {
            System.out.println("Registration completed successfully:");
            System.out.println(registration);
        }
        System.out.println();

        System.out.println("6. Student views the club page...");
        Club viewedClub = clubController.viewClubPage("C101");
        if (viewedClub != null) {
            System.out.println("--------------------------------------");
            System.out.println(viewedClub);

            System.out.println("\nClub Announcements:");
            for (Announcement a : viewedClub.getAnnouncements()) {
                System.out.println("--------------------------------------");
                System.out.println(a);
            }

            System.out.println("\nClub Events:");
            for (Event e : viewedClub.getEvents()) {
                System.out.println("--------------------------------------");
                System.out.println(e);
            }
        }

        System.out.println("\n======================================");
        System.out.println("   End of SunDevil Connect Demo App   ");
        System.out.println("======================================");
    }
}