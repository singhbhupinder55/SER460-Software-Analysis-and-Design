package edu.asu.sdc.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.asu.sdc.controller.AdminController;
import edu.asu.sdc.controller.ClubController;
import edu.asu.sdc.controller.EventController;
import edu.asu.sdc.controller.MembershipController;
import edu.asu.sdc.controller.ModerationController;
import edu.asu.sdc.controller.RegistrationController;

import edu.asu.sdc.model.Admin;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.ClubLeader;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.FlaggedContent;
import edu.asu.sdc.model.Student;

public class AppData {

    private static AppData instance;

    private final EventController eventController;
    private final RegistrationController registrationController;
    private final ClubController clubController;
    private final MembershipController membershipController;
    private final AdminController adminController;
    private final ModerationController moderationController;

    // -----------------------------
    // Session state
    // -----------------------------
    private Student currentStudent;
    private ClubLeader currentLeader;
    private Admin currentAdmin;
    private Club selectedClub;
    private Event selectedEvent;

    // -----------------------------
    // Students (10)
    // -----------------------------
    private final Student student1;
    private final Student student2;
    private final Student student3;
    private final Student student4;
    private final Student student5;
    private final Student student6;
    private final Student student7;
    private final Student student8;
    private final Student student9;
    private final Student student10;

    // -----------------------------
    // Leaders (5)
    // -----------------------------
    private final ClubLeader leader1;
    private final ClubLeader leader2;
    private final ClubLeader leader3;
    private final ClubLeader leader4;
    private final ClubLeader leader5;

    // -----------------------------
    // Admins (3)
    // -----------------------------
    private final Admin admin1;
    private final Admin admin2;
    private final Admin admin3;

    // -----------------------------
    // Clubs (5)
    // -----------------------------
    private final Club club1;
    private final Club club2;
    private final Club club3;
    private final Club club4;
    private final Club club5;

    private AppData() {
        eventController = new EventController();
        registrationController = new RegistrationController();
        clubController = new ClubController();
        membershipController = new MembershipController();
        adminController = new AdminController();
        moderationController = new ModerationController();

        // -----------------------------
        // Students
        // -----------------------------
        student1 = new Student("S101", "Bhupinder Singh", "bsingh55@asu.edu", "Active", "Software Engineering", 2026);
        student2 = new Student("S102", "Emma Carter", "ecarter@asu.edu", "Active", "Computer Science", 2027);
        student3 = new Student("S103", "Daniel Lee", "dlee@asu.edu", "Active", "Informatics", 2026);
        student4 = new Student("S104", "Olivia Brown", "obrown@asu.edu", "Active", "Data Science", 2027);
        student5 = new Student("S105", "Noah Patel", "npatel@asu.edu", "Active", "Software Engineering", 2028);
        student6 = new Student("S106", "Sophia Kim", "skim@asu.edu", "Active", "Computer Systems Engineering", 2026);
        student7 = new Student("S107", "Liam Garcia", "lgarcia@asu.edu", "Active", "Business Data Analytics", 2027);
        student8 = new Student("S108", "Ava Wilson", "awilson@asu.edu", "Active", "Cybersecurity", 2026);
        student9 = new Student("S109", "Ethan Nguyen", "enguyen@asu.edu", "Active", "Computer Science", 2028);
        student10 = new Student("S110", "Mia Thompson", "mthompson@asu.edu", "Active", "Digital Culture", 2027);

        // -----------------------------
        // Leaders
        // -----------------------------
        leader1 = new ClubLeader("L101", "Alex Johnson", "alex@asu.edu", "Active", "President");
        leader2 = new ClubLeader("L102", "Maya Patel", "maya@asu.edu", "Active", "President");
        leader3 = new ClubLeader("L103", "Jordan Blake", "jblake@asu.edu", "Active", "President");
        leader4 = new ClubLeader("L104", "Sofia Ramirez", "sramirez@asu.edu", "Active", "President");
        leader5 = new ClubLeader("L105", "Kevin Chen", "kchen@asu.edu", "Active", "President");

        // -----------------------------
        // Admins
        // -----------------------------
        admin1 = new Admin("A101", "Jordan Smith", "jordan@asu.edu", "Active", 1);
        admin2 = new Admin("A102", "Priya Shah", "pshah@asu.edu", "Active", 1);
        admin3 = new Admin("A103", "Michael Torres", "mtorres@asu.edu", "Active", 1);

        // -----------------------------
        // Clubs
        // -----------------------------
        club1 = new Club("C101", "Coding Club",
                "A club for students interested in programming, projects, and hackathons.",
                "Tech", "Pending");

        club2 = new Club("C102", "Robotics Club",
                "A club focused on robotics, embedded systems, and engineering builds.",
                "Tech", "Pending");

        club3 = new Club("C103", "Music Society",
                "A club for student performers, musicians, and open mic events.",
                "Music", "Pending");

        club4 = new Club("C104", "Career Network",
                "A professional development club focused on resumes, interviews, and networking.",
                "Career", "Pending");

        club5 = new Club("C105", "Social Impact Club",
                "A club that organizes service events, community projects, and social engagement.",
                "Social", "Pending");

        initializeData();
        initializeDefaultSession();
    }

    private void initializeData() {
        // -----------------------------
        // Add clubs
        // -----------------------------
        for (Club club : getAllClubs()) {
            clubController.addClub(club);
            adminController.addClub(club);
        }

        // -----------------------------
        // Add users to admin controller
        // -----------------------------
        for (Student s : getAllStudents()) {
            adminController.addUser(s);
        }
        for (ClubLeader leader : getAllLeaders()) {
            adminController.addUser(leader);
        }
        for (Admin admin : getAllAdmins()) {
            adminController.addUser(admin);
        }

        // -----------------------------
        // Club status
        // -----------------------------
        adminController.approveClub("C101");
        adminController.approveClub("C102");
        adminController.approveClub("C103");
        adminController.approveClub("C104");
        adminController.approveClub("C105");

        // -----------------------------
        // Events (15 total)
        // -----------------------------
        // Coding Club
        eventController.createEvent(club1, new Event(
                "E101", "Advanced Coding Workshop",
                "Intermediate and advanced Java programming concepts.",
                LocalDateTime.of(2026, 4, 10, 17, 0),
                "Fulton Center", "Tech", false, 20, 50, "Active"
        ));

        eventController.createEvent(club1, new Event(
                "E102", "Hackathon Prep Night",
                "Team formation, project brainstorming, and development strategy session.",
                LocalDateTime.of(2026, 4, 25, 19, 0),
                "Brickyard", "Tech", false, 25, 60, "Active"
        ));

        eventController.createEvent(club1, new Event(
                "E103", "Git and GitHub Bootcamp",
                "Hands-on workshop on version control and collaboration workflows.",
                LocalDateTime.of(2026, 5, 2, 18, 0),
                "Memorial Union", "Tech", false, 17, 45, "Active"
        ));

        // Robotics Club
        eventController.createEvent(club2, new Event(
                "E201", "Robotics Build Session",
                "Hands-on club meeting to assemble and test robotics components.",
                LocalDateTime.of(2026, 4, 18, 16, 0),
                "Engineering Building", "Tech", false, 16, 35, "Active"
        ));

        eventController.createEvent(club2, new Event(
                "E202", "Autonomous Drone Demo",
                "Live demonstration of autonomous drone navigation and control.",
                LocalDateTime.of(2026, 4, 28, 15, 30),
                "Engineering Building", "Tech", true, 22, 45, "Active"
        ));

        eventController.createEvent(club2, new Event(
                "E203", "Intro to Arduino Systems",
                "Learn embedded hardware basics with Arduino projects.",
                LocalDateTime.of(2026, 5, 5, 17, 30),
                "Tech Center Lab", "Tech", false, 14, 30, "Active"
        ));

        // Music Society
        eventController.createEvent(club3, new Event(
                "E301", "Open Mic Night",
                "Live student performances and music.",
                LocalDateTime.of(2026, 4, 15, 19, 0),
                "Student Pavilion", "Music", true, 15, 100, "Cancelled"
        ));

        eventController.createEvent(club3, new Event(
                "E302", "Acoustic Jam Session",
                "Casual acoustic jam session for student musicians.",
                LocalDateTime.of(2026, 4, 27, 18, 30),
                "Music Room A", "Music", false, 13, 40, "Active"
        ));

        eventController.createEvent(club3, new Event(
                "E303", "Spring Showcase Rehearsal",
                "Performance preparation for the spring music showcase.",
                LocalDateTime.of(2026, 5, 1, 17, 0),
                "Performing Arts Hall", "Music", false, 19, 50, "Active"
        ));

        // Career Network
        eventController.createEvent(club4, new Event(
                "E401", "Resume Workshop",
                "A career-focused session on resumes, portfolios, and internship prep.",
                LocalDateTime.of(2026, 4, 22, 18, 30),
                "Memorial Union", "Career", false, 18, 40, "Active"
        ));

        eventController.createEvent(club4, new Event(
                "E402", "Mock Interview Night",
                "Practice technical and behavioral interviews with peers.",
                LocalDateTime.of(2026, 4, 30, 18, 0),
                "Career Services Building", "Career", false, 21, 35, "Active"
        ));

        eventController.createEvent(club4, new Event(
                "E403", "LinkedIn and Networking Session",
                "Build a stronger online profile and networking strategy.",
                LocalDateTime.of(2026, 5, 6, 17, 0),
                "Career Services Building", "Career", false, 12, 50, "Active"
        ));

        // Social Impact Club
        eventController.createEvent(club5, new Event(
                "E501", "Community Clean-Up Day",
                "Volunteer event focused on local neighborhood clean-up.",
                LocalDateTime.of(2026, 4, 20, 9, 0),
                "Tempe Town Lake", "Social", false, 23, 80, "Active"
        ));

        eventController.createEvent(club5, new Event(
                "E502", "Food Drive Coordination Meeting",
                "Planning session for the upcoming student food drive.",
                LocalDateTime.of(2026, 4, 26, 14, 0),
                "Student Center West", "Social", false, 11, 30, "Active"
        ));

        eventController.createEvent(club5, new Event(
                "E503", "Volunteer Appreciation Mixer",
                "Social mixer for active volunteers and new members.",
                LocalDateTime.of(2026, 5, 8, 18, 30),
                "Student Pavilion", "Social", true, 16, 60, "Active"
        ));

        // Ensure cancelled status remains cancelled
        eventController.cancelEvent("E301");

        // -----------------------------
        // Announcements
        // -----------------------------
        clubController.postAnnouncement(club1, new Announcement(
                "A101", "Welcome Message",
                "Welcome to the coding club! Check out our upcoming workshops and hackathon events.",
                LocalDateTime.of(2026, 4, 1, 10, 0)
        ));
        clubController.postAnnouncement(club1, new Announcement(
                "A102", "Hackathon Update",
                "Teams for the hackathon prep night will be finalized during the event.",
                LocalDateTime.of(2026, 4, 8, 14, 30)
        ));

        clubController.postAnnouncement(club2, new Announcement(
                "A201", "Lab Safety Reminder",
                "Please wear proper protective gear during robotics build sessions.",
                LocalDateTime.of(2026, 4, 6, 9, 15)
        ));
        clubController.postAnnouncement(club2, new Announcement(
                "A202", "Drone Demo Logistics",
                "Please arrive 15 minutes early for setup and check-in.",
                LocalDateTime.of(2026, 4, 12, 11, 0)
        ));

        clubController.postAnnouncement(club3, new Announcement(
                "A301", "Performance Sign-Up",
                "Musicians can now sign up for the spring showcase.",
                LocalDateTime.of(2026, 4, 7, 16, 20)
        ));

        clubController.postAnnouncement(club4, new Announcement(
                "A401", "Internship Prep",
                "Bring a resume draft to the workshop for better feedback.",
                LocalDateTime.of(2026, 4, 9, 13, 45)
        ));

        clubController.postAnnouncement(club5, new Announcement(
                "A501", "Volunteer Orientation",
                "New members should complete orientation before service events.",
                LocalDateTime.of(2026, 4, 10, 8, 30)
        ));

        // -----------------------------
        // Approved members only
        // Keep club pages populated, but leave pending requests empty
        // -----------------------------
        club1.addMember(student2);   // Coding Club
        club2.addMember(student5);   // Robotics Club
        club3.addMember(student7);   // Music Society
        club4.addMember(student9);   // Career Network
        club5.addMember(student1);   // Social Impact Club

        // -----------------------------
        // Flagged content
        // -----------------------------
        moderationController.addFlaggedContent(new FlaggedContent(
                "F101", "Announcement",
                "Inappropriate language in announcement",
                "Open",
                LocalDate.of(2026, 3, 30)
        ));

        moderationController.addFlaggedContent(new FlaggedContent(
                "F102", "Club",
                "Club description contains misleading information",
                "Open",
                LocalDate.of(2026, 3, 30)
        ));

        moderationController.addFlaggedContent(new FlaggedContent(
                "F103", "Event",
                "Event listing contains incorrect pricing information",
                "Open",
                LocalDate.of(2026, 4, 2)
        ));

        moderationController.addFlaggedContent(new FlaggedContent(
                "F104", "Announcement",
                "Possible spam content reported by a user",
                "Open",
                LocalDate.of(2026, 4, 4)
        ));

        moderationController.addFlaggedContent(new FlaggedContent(
                "F105", "Club",
                "Reported for unclear eligibility language",
                "Open",
                LocalDate.of(2026, 4, 6)
        ));

        // -----------------------------
        // Optional sample suspensions
        // -----------------------------
        adminController.suspendUser("S110");
    }

    private void initializeDefaultSession() {
        currentStudent = student1;
        currentLeader = leader1;
        currentAdmin = admin1;
        selectedClub = club1;
        selectedEvent = null;
    }

    public static AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    // -----------------------------
    // Controllers
    // -----------------------------
    public EventController getEventController() {
        return eventController;
    }

    public RegistrationController getRegistrationController() {
        return registrationController;
    }

    public ClubController getClubController() {
        return clubController;
    }

    public MembershipController getMembershipController() {
        return membershipController;
    }

    public AdminController getAdminController() {
        return adminController;
    }

    public ModerationController getModerationController() {
        return moderationController;
    }

    // -----------------------------
    // Session getters/setters
    // -----------------------------
    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public ClubLeader getCurrentLeader() {
        return currentLeader;
    }

    public void setCurrentLeader(ClubLeader currentLeader) {
        this.currentLeader = currentLeader;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public void setCurrentAdmin(Admin currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public Club getSelectedClub() {
        return selectedClub;
    }

    public void setSelectedClub(Club selectedClub) {
        this.selectedClub = selectedClub;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    // -----------------------------
    // All users / entities
    // -----------------------------
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        return students;
    }

    public List<ClubLeader> getAllLeaders() {
        List<ClubLeader> leaders = new ArrayList<>();
        leaders.add(leader1);
        leaders.add(leader2);
        leaders.add(leader3);
        leaders.add(leader4);
        leaders.add(leader5);
        return leaders;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        admins.add(admin1);
        admins.add(admin2);
        admins.add(admin3);
        return admins;
    }

    public List<Club> getAllClubs() {
        List<Club> clubs = new ArrayList<>();
        clubs.add(club1);
        clubs.add(club2);
        clubs.add(club3);
        clubs.add(club4);
        clubs.add(club5);
        return clubs;
    }

    // -----------------------------
    // Legacy / convenience getters
    // -----------------------------
    public Student getStudent() {
        return student1;
    }

    public ClubLeader getLeader() {
        return leader1;
    }

    public Admin getAdmin() {
        return admin1;
    }

    public Club getClub() {
        return club1;
    }

    public Club getRoboticsClub() {
        return club2;
    }

    public Club getMusicClub() {
        return club3;
    }

    // -----------------------------
    // Lookup helpers
    // -----------------------------
    public Student findStudentById(String studentId) {
        for (Student student : getAllStudents()) {
            if (student.getUserId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }
        return null;
    }

    public ClubLeader findLeaderById(String leaderId) {
        for (ClubLeader leader : getAllLeaders()) {
            if (leader.getUserId().equalsIgnoreCase(leaderId)) {
                return leader;
            }
        }
        return null;
    }

    public Admin findAdminById(String adminId) {
        for (Admin admin : getAllAdmins()) {
            if (admin.getUserId().equalsIgnoreCase(adminId)) {
                return admin;
            }
        }
        return null;
    }

    public Club findClubByEvent(Event targetEvent) {
        if (targetEvent == null) {
            return null;
        }

        for (Club club : getAllClubs()) {
            for (Event event : club.getEvents()) {
                if (event.getEventId().equalsIgnoreCase(targetEvent.getEventId())) {
                    return club;
                }
            }
        }
        return null;
    }

    public Club findClubByLeader(ClubLeader targetLeader) {
        if (targetLeader == null) {
            return null;
        }

        if (targetLeader.getUserId().equalsIgnoreCase(leader1.getUserId())) {
            return club1;
        }
        if (targetLeader.getUserId().equalsIgnoreCase(leader2.getUserId())) {
            return club2;
        }
        if (targetLeader.getUserId().equalsIgnoreCase(leader3.getUserId())) {
            return club3;
        }
        if (targetLeader.getUserId().equalsIgnoreCase(leader4.getUserId())) {
            return club4;
        }
        if (targetLeader.getUserId().equalsIgnoreCase(leader5.getUserId())) {
            return club5;
        }

        return null;
    }
}