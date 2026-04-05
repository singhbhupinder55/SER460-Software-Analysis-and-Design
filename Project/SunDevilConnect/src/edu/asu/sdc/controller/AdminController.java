package edu.asu.sdc.controller;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.User;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    private List<Club> clubs;
    private List<User> users;

    public AdminController() {
        this.clubs = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<Club> viewAllClubs() {
        return clubs;
    }

    public boolean approveClub(String clubId) {
        for (Club club : clubs) {
            if (club.getClubId().equalsIgnoreCase(clubId)) {
                club.setStatus("Approved");
                return true;
            }
        }
        return false;
    }

    public boolean denyClub(String clubId) {
        for (Club club : clubs) {
            if (club.getClubId().equalsIgnoreCase(clubId)) {
                club.setStatus("Denied");
                return true;
            }
        }
        return false;
    }

    public boolean suspendClub(String clubId) {
        for (Club club : clubs) {
            if (club.getClubId().equalsIgnoreCase(clubId)) {
                club.setStatus("Suspended");
                return true;
            }
        }
        return false;
    }

    public boolean suspendUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equalsIgnoreCase(userId)) {
                user.setStatus("Suspended");
                return true;
            }
        }
        return false;
    }
    
}