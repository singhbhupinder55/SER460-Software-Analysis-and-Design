package edu.asu.sdc.model;

public class User {
    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void browseEvents() {
        System.out.println(name + " is browsing events.");
    }

    public void viewClubPage(String clubId) {
        System.out.println(name + " is viewing club page: " + clubId);
    }

    protected boolean validateEmail(String email) {
        return email != null && email.contains("@");
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
                "\nName: " + name +
                "\nEmail: " + email;
    }
}