package edu.asu.sdc.controller;
import edu.asu.sdc.model.Announcement;
import edu.asu.sdc.model.Club;
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

    public List<Club> getAllClubs() {
        return clubs;
    }
}