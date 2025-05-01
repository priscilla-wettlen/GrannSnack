package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "booking_day", columnDefinition = "INT NOT NULL")
    private int day;

    @Column(name = "booking_time_slot", columnDefinition = "INT NOT NULL")
    private int timeSlot;

    @Column(name = "booking_month", columnDefinition = "INT NOT NULL")
    private int month;

    @Column(name = "booking_year", columnDefinition = "INT NOT NULL")
    private int year;

    @Column(name = "booking_notes", columnDefinition = "VARCHAR(255)")
    private String notes;

    @Column(name = "booking_user_id", columnDefinition = "BIGINT")
    private int userId;

    @Column(name = "booking_created_at", columnDefinition = "DATETIME")
    private LocalDateTime createdAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int week) {
        this.month = week;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        return day + "_" + timeSlot;
    }

    // Lifecycle callback
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}