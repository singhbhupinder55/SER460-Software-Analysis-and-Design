package app.control;

import app.model.Role;
import app.model.User;
import app.storage.DataStore;

public class Login {

    private final DataStore store;

    public Login(DataStore store) {
        this.store = store;
    }

    public User authenticate(String username, String password) {
        if (username == null || password == null) return null;

        username = username.trim();
        password = password.trim();

        // 1) Staff logins from users file
        User staff = store.authenticateStaff(username, password);
        if (staff != null) return staff;

        // 2) Patient login: username=patientId, password=lastName 
        if (store.authenticatePatient(username, password)) {
            return new User(username, Role.PATIENT);
        }

        return null;
    }
}