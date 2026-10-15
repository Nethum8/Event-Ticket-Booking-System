package com.ticket_booking.app.event;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMergeSorter {

    // 1. Recursive Merge Sort by Popularity (Descending Order: Highest Popularity First)
    public List<Event> sortByPopularity(List<Event> events) {
        if (events == null || events.size() <= 1) {
            return events;
        }

        int mid = events.size() / 2;
        List<Event> left = sortByPopularity(new ArrayList<>(events.subList(0, mid)));
        List<Event> right = sortByPopularity(new ArrayList<>(events.subList(mid, events.size())));

        return mergeByPopularity(left, right);
    }

    private List<Event> mergeByPopularity(List<Event> left, List<Event> right) {
        List<Event> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            // Compare popularity scores (Descending order)
            if (left.get(i).getPopularityScore() >= right.get(j).getPopularityScore()) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        // Copy remaining elements
        while (i < left.size()) {
            merged.add(left.get(i++));
        }
        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }

    // 2. Recursive Merge Sort by Event Date (Ascending Order: Nearest Date First)
    public List<Event> sortByDate(List<Event> events) {
        if (events == null || events.size() <= 1) {
            return events;
        }

        int mid = events.size() / 2;
        List<Event> left = sortByDate(new ArrayList<>(events.subList(0, mid)));
        List<Event> right = sortByDate(new ArrayList<>(events.subList(mid, events.size())));

        return mergeByDate(left, right);
    }

    private List<Event> mergeByDate(List<Event> left, List<Event> right) {
        List<Event> merged = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            // Compare event dates (Ascending order: earliest date first)
            if (!left.get(i).getEventDate().isAfter(right.get(j).getEventDate())) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }

        // Copy remaining elements
        while (i < left.size()) {
            merged.add(left.get(i++));
        }
        while (j < right.size()) {
            merged.add(right.get(j++));
        }

        return merged;
    }
}