package edu.asu.sdc.model;

public class Admin extends User {
    private int adminLevel;

    public Admin(String userId, String name, String email, String status, int adminLevel) {
        super(userId, name, email, status);
        this.adminLevel = adminLevel;
    }

    public int getAdminLevel() {
        return adminLevel;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nAdmin Level: " + adminLevel;
    }
}