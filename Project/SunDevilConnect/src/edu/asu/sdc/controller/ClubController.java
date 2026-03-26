package edu.asu.sdc.controller;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.MembershipRequest;
import edu.asu.sdc.model.Student;

import java.util.ArrayList;
import java.util.List;

public class ClubController {
    private List<Club> clubs;

    public ClubController() {
        this.clubs = new ArrayList<>();
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public Club viewClubPage(String clubId) {
        for (Club club : clubs) {
            if (club.getClubId().equalsIgnoreCase(clubId)) {
                return club;
            }
        }
        return null;
    }

    public Announcement postAnnouncement(Club club, Announcement announcement) {
        club.addAnnouncement(announcement);
        return announcement;
    }
    
    public MembershipRequest requestToJoinClub(Student student, Club club) {
        for (MembershipRequest request : club.getMembershipRequests()) {
            if (request.getStudent().getUserId().equalsIgnoreCase(student.getUserId())
                    && request.getStatus().equalsIgnoreCase("Pending")) {
                return null;
            }
        }

        MembershipRequest request = new MembershipRequest(
                "MR-" + student.getUserId() + "-" + club.getClubId(),
                "2026-03-22",
                "Pending",
                student,
                club
        );

        club.addMembershipRequest(request);
        return request;
    }

    public boolean approveMembershipRequest(Club club, String requestId) {
        for (MembershipRequest request : club.getMembershipRequests()) {
            if (request.getRequestId().equalsIgnoreCase(requestId)
                    && request.getStatus().equalsIgnoreCase("Pending")) {
                request.approve();
                return club.addMember(request.getStudent());
            }
        }
        return false;
    }


    public List<Club> getAllClubs() {
        return clubs;
    }
}