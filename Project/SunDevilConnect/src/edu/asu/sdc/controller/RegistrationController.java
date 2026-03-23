package edu.asu.sdc.controller;
import edu.asu.sdc.model.Event;
import edu.asu.sdc.model.EventRegistration;
import edu.asu.sdc.model.Student;
import java.util.ArrayList;
import java.util.List;

public class RegistrationController {
    private List<EventRegistration> registrations;

    public RegistrationController() {
        this.registrations = new ArrayList<>();
    }

    public EventRegistration register(Student student, Event event) {
        if (event.isFull(registrations.size())) {
            System.out.println("Registration failed: Event is full.");
            return null;
        }

        EventRegistration registration = student.registerForEvent(event);
        registrations.add(registration);
        return registration;
    }

    public void cancelRegistration(String registrationId) {
        for (EventRegistration registration : registrations) {
            if (registration.getRegistrationId().equalsIgnoreCase(registrationId)) {
                registration.cancel();
                break;
            }
        }
    }

    public List<EventRegistration> getAllRegistrations() {
        return registrations;
    }
}