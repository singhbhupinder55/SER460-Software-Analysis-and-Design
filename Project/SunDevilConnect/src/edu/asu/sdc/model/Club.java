package edu.asu.sdc.model;
import java.util.ArrayList;
import java.util.List;

public class Club {
    private String clubId;
    private String name;
    private String description;
    private String category;
    private String status;
    private List<Event> events;
    private List<Announcement> announcements;
    private List<Student> members;
    private List<MembershipRequest> membershipRequests;

    public Club(String clubId, String name, String description, String category, String status) {
        this.clubId = clubId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.events = new ArrayList<>();
        this.announcements = new ArrayList<>();
        this.members = new ArrayList<>();
        this.membershipRequests = new ArrayList<>();
    }

    public String getClubId() {
        return clubId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public List<Student> getMembers() {
        return members;
    }

    public List<MembershipRequest> getMembershipRequests() {
        return membershipRequests;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addAnnouncement(Announcement announcement) {
        announcements.add(announcement);
    }

    public boolean addMember(Student student) {
        if (!members.contains(student)) {
            members.add(student);
            return true;
        }
        return false;
    }

    public void addMembershipRequest(MembershipRequest request) {
        membershipRequests.add(request);
    }

    @Override
    public String toString() {
        return "Club ID: " + clubId +
                "\nName: " + name +
                "\nDescription: " + description +
                "\nCategory: " + category +
                "\nStatus: " + status;
    }
}