package edu.asu.sdc.model;

public class MembershipRequest {
    private String requestId;
    private String requestDate;
    private String status;
    private Student student;
    private Club club;

    public MembershipRequest(String requestId, String requestDate, String status, Student student, Club club) {
        this.requestId = requestId;
        this.requestDate = requestDate;
        this.status = status;
        this.student = student;
        this.club = club;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Club getClub() {
        return club;
    }

    public void approve() {
        this.status = "Approved";
    }

    public void reject() {
        this.status = "Rejected";
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
                "\nStudent: " + student.getName() +
                "\nClub: " + club.getName() +
                "\nRequest Date: " + requestDate +
                "\nStatus: " + status;
    }
}