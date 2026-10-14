package com.ticket_booking.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMergeSorter eventMergeSorter;

    // 1. Create a new event
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // 2. Get all events (unsorted)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // 3. Get events sorted by Popularity using custom Merge Sort
    public List<Event> getEventsSortedByPopularity() {
        List<Event> allEvents = eventRepository.findAll();
        return eventMergeSorter.sortByPopularity(allEvents);
    }

    // 4. Get events sorted by Date using custom Merge Sort
    public List<Event> getEventsSortedByDate() {
        List<Event> allEvents = eventRepository.findAll();
        return eventMergeSorter.sortByDate(allEvents);
    }

    // 5. Update an existing event
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> existingOpt = eventRepository.findById(id);
        if (existingOpt.isPresent()) {
            Event existing = existingOpt.get();
            existing.setTitle(updatedEvent.getTitle());
            existing.setCategory(updatedEvent.getCategory());
            existing.setLocation(updatedEvent.getLocation());
            existing.setEventDate(updatedEvent.getEventDate());
            existing.setTicketPrice(updatedEvent.getTicketPrice());
            existing.setAvailableSeats(updatedEvent.getAvailableSeats());
            existing.setPopularityScore(updatedEvent.getPopularityScore());
            return eventRepository.save(existing);
        }
        return null;
    }

    // 6. Delete an event
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
