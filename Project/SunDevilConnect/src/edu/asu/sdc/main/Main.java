package edu.asu.sdc.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import edu.asu.sdc.controller.ClubController;
import edu.asu.sdc.controller.EventController;
import edu.asu.sdc.controller.RegistrationController;
import edu.asu.sdc.controller.AdminController;
import edu.asu.sdc.controller.ModerationController;
import edu.asu.sdc.controller.MembershipController;

import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.ClubLeader;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.EventRegistration;
import edu.asu.sdc.model.MembershipRequest;
import edu.asu.sdc.model.Student;
import edu.asu.sdc.model.Admin;
import edu.asu.sdc.model.FlaggedContent;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventController eventController = new EventController();
        RegistrationController registrationController = new RegistrationController();
        ClubController clubController = new ClubController();
        MembershipController membershipController = new MembershipController();
        AdminController adminController = new AdminController();
        ModerationController moderationController = new ModerationController();

        Student student = new Student(
                "S101",
                "Bhupinder",
                "bsingh55@asu.edu",
                "Active",
                "Software Engineering",
                2026
        );

        ClubLeader leader = new ClubLeader(
                "L101",
                "Alex Johnson",
                "alex@asu.edu",
                "Active",
                "President"
        );

        Club club = new Club(
        "C101",
        "Coding Club",
        "A club for students interested in programming.",
        "Tech",
        "Pending"
        );

        Club roboticsClub = new Club(
        "C102",
        "Robotics Club",
        "A club focused on robotics and engineering projects.",
        "Tech",
        "Pending"
        );

        Club musicClub = new Club(
        "C103",
        "Music Society",
        "A club for music lovers and performers.",
        "Music",
        "Pending"
        );

        Admin admin = new Admin(
        "A101",
        "Jordan Smith",
        "jordan@asu.edu",
        "Active",
        1
        );

        FlaggedContent flaggedAnnouncement = new FlaggedContent(
        "F101",
        "Announcement",
        "Inappropriate language in announcement",
        "Open",
        LocalDate.of(2026, 3, 30)
        );

        FlaggedContent flaggedClub = new FlaggedContent(
        "F102",
        "Club",
        "Club description contains misleading information",
        "Open",
        LocalDate.of(2026, 3, 30)
        );

        Event techEvent = new Event(
                "E101",
                "Coding Workshop",
                "Intro to Java programming",
                LocalDateTime.of(2026, 4, 10, 17, 0),
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
                LocalDateTime.of(2026, 4, 15, 19, 0),
                "Student Pavilion",
                "Music",
                true,
                15,
                100,
                "Active"
        );

        Event updatedTechEvent = new Event(
        "E101",
        "Advanced Coding Workshop",
        "Intermediate and advanced Java programming concepts.",
        LocalDateTime.of(2026, 4, 10, 17, 0),
        "Fulton Center",
        "Tech",
        false,
        20,
        50,
        "Active"
        );

        Announcement announcement = new Announcement(
                "A101",
                "Welcome Message",
                "Welcome to the coding club!",
                LocalDateTime.of(2026, 4, 1, 10, 0)
        );

        System.out.println("======================================");
        System.out.println("      SunDevil Connect Demo App       ");
        System.out.println("======================================\n");

        System.out.println("1. Setting up clubs...");
        clubController.addClub(club);
        clubController.addClub(roboticsClub);
        clubController.addClub(musicClub);

        adminController.addClub(club);
        adminController.addClub(roboticsClub);
        adminController.addClub(musicClub);

        adminController.addUser(student);
        adminController.addUser(leader);
        adminController.addUser(admin);

        moderationController.addFlaggedContent(flaggedAnnouncement);
        moderationController.addFlaggedContent(flaggedClub);

        System.out.println("Clubs added successfully.\n");

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

        System.out.println("4. Club leader edits Coding Workshop...");
        eventController.updateEvent("E101", updatedTechEvent);
        System.out.println("Coding Workshop updated successfully.\n");

        System.out.println("5. Club leader cancels Open Mic Night...");
        eventController.cancelEvent("E102");
        System.out.println("Open Mic Night cancelled successfully.\n");

        System.out.println("6. Admin views all clubs...");
        for (Club c : adminController.viewAllClubs()) {
        System.out.println("--------------------------------------");
        System.out.println(c);
        }
        System.out.println();

        System.out.println("7. Admin approves Coding Club...");
        if (adminController.approveClub("C101")) {
        System.out.println("Coding Club approved successfully.");
        }
        System.out.println();

        System.out.println("8. Admin denies Music Society...");
        if (adminController.denyClub("C103")) {
        System.out.println("Music Society denied successfully.");
        }
        System.out.println();

        System.out.println("9. Admin suspends Robotics Club...");
        if (adminController.suspendClub("C102")) {
        System.out.println("Robotics Club suspended successfully.");
        }
        System.out.println();

        System.out.println("10. Admin reviews flagged content...");
        for (FlaggedContent flaggedContent : moderationController.reviewFlaggedContent()) {
        System.out.println("--------------------------------------");
        System.out.println(flaggedContent);
        }
        System.out.println();

        System.out.println("11. Admin resolves flagged announcement...");
        if (moderationController.resolveFlaggedContent("F101")) {
        System.out.println("Flagged announcement resolved successfully.");
        }
        System.out.println();

        System.out.println("12. Admin dismisses flagged club report...");
        if (moderationController.dismissFlaggedContent("F102")) {
        System.out.println("Flagged club report dismissed successfully.");
        }
        System.out.println();

        System.out.println("13. Admin suspends student user...");
        if (adminController.suspendUser("S101")) {
        System.out.println("Student user suspended successfully.");
        }
        System.out.println();

        System.out.println("14. Student browses all available events...");
        List<Event> allEvents = eventController.browseEvents();
        for (Event e : allEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("15. Student searches for events with keyword: 'Coding'");
            List<Event> searchResults = eventController.searchEvents("Coding");
            for (Event e : searchResults) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("16. Student filters events by category: Tech");
        List<Event> techEvents = eventController.filterEvents("Tech");
        for (Event e : techEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        System.out.println("17. Student filters events by paid status: Free events only");
        List<Event> freeEvents = eventController.filterByPaidStatus(false);
        for (Event e : freeEvents) {
            System.out.println("--------------------------------------");
            System.out.println(e);
        }
        System.out.println();

        List<Event> locationEvents = eventController.filterByLocation("Fulton Center");
        System.out.println("18. Student filters events by location: Fulton Center");
        for (Event e : locationEvents) {
        System.out.println("--------------------------------------");
        System.out.println(e);
        }
        System.out.println();

        System.out.println("19. Student filters events by date: 2026-04-10");
        List<Event> dateEvents = eventController.filterByDate(LocalDate.of(2026, 4, 10));
        for (Event e : dateEvents) {
        System.out.println("--------------------------------------");
        System.out.println(e);
        }
        System.out.println();

        System.out.println("20. Student filters events by popularity: 18 or higher");
        List<Event> popularEvents = eventController.filterByPopularity(18);
        for (Event e : popularEvents) {
        System.out.println("--------------------------------------");
        System.out.println(e);
        }
        System.out.println();

        System.out.println("21. Student requests to join the club...");
        MembershipRequest membershipRequest = membershipController.requestToJoinClub(student, club);
        if (membershipRequest != null) {
        System.out.println("Membership request submitted successfully:");
        System.out.println(membershipRequest);
        } else {
        System.out.println("A pending membership request already exists for this student.");
        }
        System.out.println();

        System.out.println("22. Club leader approves the membership request...");
        boolean approved = membershipController.approveMembershipRequest(club, "MR-S101-C101");
        if (approved) {
        System.out.println("Membership request approved successfully.\n");
        } else {
        System.out.println("Membership request approval failed.\n");
        }

        System.out.println("23. Student registers for an event...");
        EventRegistration registration = registrationController.register(student, techEvent);
        if (registration != null) {
             System.out.println("Registration completed successfully:");
            System.out.println(registration);
        }
        System.out.println();

        System.out.println("24. Student tries to register for the same event again...");
        EventRegistration duplicateRegistration = registrationController.register(student, techEvent);
        if (duplicateRegistration == null) {
            System.out.println("Duplicate registration was prevented successfully.");
        }
        System.out.println();

        System.out.println("25. Student views the club page...");
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