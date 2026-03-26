package edu.asu.sdc.main;

import edu.asu.sdc.controller.ClubController;
import edu.asu.sdc.controller.EventController;
import edu.asu.sdc.controller.RegistrationController;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.ClubLeader;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.EventRegistration;
import edu.asu.sdc.model.MembershipRequest;
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

        Event techEvent = new Event(
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

        Event musicEvent = new Event(
                "E102",
                "Open Mic Night",
                "Live student performances and music.",
                "2026-04-15 7:00 PM",
                "Student Pavilion",
                "Music",
                true,
                15,
                100,
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

        System.out.println("2. Club leader creates events...");
        leader.createEvent(techEvent);
        eventController.createEvent(club, techEvent);

        leader.createEvent(musicEvent);
        eventController.createEvent(club, musicEvent);

        System.out.println("Events created successfully.\n");

        System.out.println("3. Club leader posts an announcement...");
        leader.postAnnouncement(announcement);
        clubController.postAnnouncement(club, announcement);
        System.out.println("Announcement posted successfully.\n");

        System.out.println("4. Student browses all available events...");
        List<Event> allEvents = eventController.browseEvents();
        for (Event e : allEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("5. Student searches for events with keyword: 'Coding'");
            List<Event> searchResults = eventController.searchEvents("Coding");
            for (Event e : searchResults) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("6. Student filters events by category: Tech");
        List<Event> techEvents = eventController.filterEvents("Tech");
        for (Event e : techEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("7. Student filters events by paid status: Free events only");
        List<Event> freeEvents = eventController.filterByPaidStatus(false);
        for (Event e : freeEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("8. Student filters events by location: Engineering Building");
        List<Event> locationEvents = eventController.filterByLocation("Engineering Building");
        for (Event e : locationEvents) {
        System.out.println("--------------------------------------");
        System.out.println(e);
        }
        System.out.println();

        System.out.println("9. Student requests to join the club...");
        MembershipRequest membershipRequest = clubController.requestToJoinClub(student, club);
        if (membershipRequest != null) {
            System.out.println("Membership request submitted successfully:");
            System.out.println(membershipRequest);
        } else {
            System.out.println("A pending membership request already exists for this student.");
        }
        System.out.println();

        System.out.println("10. Club leader approves the membership request...");
        boolean approved = clubController.approveMembershipRequest(club, "MR-S101-C101");
        if (approved) {
            System.out.println("Membership request approved successfully.\n");
        } else {
            System.out.println("Membership request approval failed.\n");
        }

        System.out.println("11. Student registers for an event...");
        EventRegistration registration = registrationController.register(student, techEvent);
        if (registration != null) {
             System.out.println("Registration completed successfully:");
            System.out.println(registration);
        }
        System.out.println();

        System.out.println("12. Student tries to register for the same event again...");
        EventRegistration duplicateRegistration = registrationController.register(student, techEvent);
        if (duplicateRegistration == null) {
            System.out.println("Duplicate registration was prevented successfully.");
        }
        System.out.println();

        System.out.println("13. Student views the club page...");
        Club viewedClub = clubController.viewClubPage("C101");
        if (viewedClub != null) {
            System.out.println("--------------------------------------");
            System.out.println(viewedClub);

            System.out.println("\nClub Members:");
            for (Student s : viewedClub.getMembers()) {
                System.out.println("--------------------------------------");
                System.out.println(s);
            }

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