package edu.asu.sdc.controller;
import edu.asu.sdc.model.Club;
import edu.asu.sdc.model.Event;
import java.util.ArrayList;
import java.util.List;

public class EventController {
    private List<Event> events;
    private List<Club> clubs;

    public EventController() {
        this.events = new ArrayList<>();
        this.clubs = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public List<Event> browseEvents() {
        return events;
    }

    public List<Event> searchEvents(String keyword) {
        List<Event> results = new ArrayList<>();
        for (Event event : events) {
            if (event.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                event.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(event);
            }
        }
        return results;
    }

    public List<Event> filterEvents(String category) {
        List<Event> filtered = new ArrayList<>();
        for (Event event : events) {
            if (event.getCategory().equalsIgnoreCase(category)) {
                filtered.add(event);
            }
        }
        return filtered;
    }
    public List<Event> filterByPaidStatus(boolean isPaid) {
        List<Event> filtered = new ArrayList<>();
        for (Event event : events) {
            if (event.isPaid() == isPaid) {
                filtered.add(event);
            }
        }
        return filtered;
    }

    public List<Event> filterByLocation(String location) {
        List<Event> filtered = new ArrayList<>();
            for (Event event : events) {
                if (event.getLocation().equalsIgnoreCase(location)) {
                    filtered.add(event);
                }
            }
            return filtered;
        }

    public Event getEventDetails(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equalsIgnoreCase(eventId)) {
                return event;
            }
        }
        return null;
    }

    public Event createEvent(Club club, Event event) {
        club.addEvent(event);
        events.add(event);
        return event;
    }

    public Event updateEvent(String eventId, Event updatedEvent) {
        Event existingEvent = getEventDetails(eventId);
        if (existingEvent != null) {
            existingEvent.updateDetails(updatedEvent);
        }
        return existingEvent;
    }

    public void cancelEvent(String eventId) {
        Event event = getEventDetails(eventId);
        if (event != null) {
            event.cancel();
        }
    }
}