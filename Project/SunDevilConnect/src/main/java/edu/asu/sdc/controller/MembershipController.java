package edu.asu.sdc.controller;

import java.time.LocalDate;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.MembershipRequest;
import edu.asu.sdc.model.Student;

public class MembershipController {

    public MembershipRequest requestToJoinClub(Student student, Club club) {
        // block if already a member
        for (Student member : club.getMembers()) {
            if (member.getUserId().equalsIgnoreCase(student.getUserId())) {
                return null;
            }
        }

        // block duplicate pending request
        for (MembershipRequest request : club.getMembershipRequests()) {
            if (request.getStudent().getUserId().equalsIgnoreCase(student.getUserId())
                    && request.getStatus().equalsIgnoreCase("Pending")) {
                return null;
            }
        }

        MembershipRequest request = new MembershipRequest(
                "MR-" + student.getUserId() + "-" + club.getClubId(),
                LocalDate.now(),
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

    public boolean isStudentAlreadyMember(Student student, Club club) {
        for (Student member : club.getMembers()) {
            if (member.getUserId().equalsIgnoreCase(student.getUserId())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPendingRequest(Student student, Club club) {
        for (MembershipRequest request : club.getMembershipRequests()) {
            if (request.getStudent().getUserId().equalsIgnoreCase(student.getUserId())
                    && request.getStatus().equalsIgnoreCase("Pending")) {
                return true;
            }
        }
        return false;
    }
}