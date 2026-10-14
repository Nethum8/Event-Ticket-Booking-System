package com.ticket_booking.app.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    // 1. Add a new event to the catalog (POST)
    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        return ResponseEntity.ok(eventService.createEvent(event));
    }

    // 2. Get all events without sorting (GET)
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // 3. Get events sorted by Popularity using Merge Sort (GET)
    @GetMapping("/sort/popularity")
    public List<Event> getEventsByPopularity() {
        return eventService.getEventsSortedByPopularity();
    }

    // 4. Get events sorted by Date using Merge Sort (GET)
    @GetMapping("/sort/date")
    public List<Event> getEventsByDate() {
        return eventService.getEventsSortedByDate();
    }

    // 5. Update an event by ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updated = eventService.updateEvent(id, event);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 6. Delete an event by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        if (eventService.deleteEvent(id)) {
            return ResponseEntity.ok("Event deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}
