package com.ticket_booking.app.booking;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketQueue {

    // Inner Node class for Linked-Queue representation
    private static class Node {
        BookingRequest data;
        Node next;

        Node(BookingRequest data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; //
    private Node rear;  //
    private int size;

    public TicketQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // 1. Enqueue:
    public synchronized void enqueue(BookingRequest request) {
        Node newNode = new Node(request);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // 2. Dequeue:
    public synchronized BookingRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        BookingRequest temp = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return temp;
    }

    // 3. Peek:
    public BookingRequest peek() {
        return isEmpty() ? null : front.data;
    }

    // 4.
    public List<BookingRequest> getAllInQueue() {
        List<BookingRequest> list = new ArrayList<>();
        Node current = front;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int getSize() {
        return size;
    }
}