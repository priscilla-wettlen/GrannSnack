package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "booking_date", columnDefinition = "DATE NOT NULL")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlots timeSlot;

    @Column(name = "booking_notes", columnDefinition = "VARCHAR(255)")
    private String notes;

    @Column(name = "booking_user_id", columnDefinition = "INT")
    private int userId;

    @Column(name = "booking_created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TimeSlots getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlots timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper method to generate a unique key for this booking
    public String getSlotKey() {
        return date + "_" + timeSlot;
    }

    // Lifecycle callback
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}