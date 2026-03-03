package app.control;

import app.model.User;

public class Session {
    private static User currentUser;

    public static User getUser() { return currentUser; }
    public static void setUser(User user) { currentUser = user; }
    public static void clear() { currentUser = null; }
}