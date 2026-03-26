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
        int currentRegistrationsForEvent = 0;

        for (EventRegistration registration : registrations) {
            if (registration.getEventId().equalsIgnoreCase(event.getEventId())
                    && registration.getStatus().equalsIgnoreCase("Confirmed")) {
                currentRegistrationsForEvent++;
            }

            if (registration.getStudentId().equalsIgnoreCase(student.getUserId())
                    && registration.getEventId().equalsIgnoreCase(event.getEventId())
                    && registration.getStatus().equalsIgnoreCase("Confirmed")) {
                System.out.println("Registration failed: Student is already registered for this event.");
                return null;
            }
        }

        if (event.isFull(currentRegistrationsForEvent)) {
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