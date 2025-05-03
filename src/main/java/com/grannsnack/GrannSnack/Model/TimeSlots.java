package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "time_slots")
public class TimeSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "label", columnDefinition = "VARCHAR(255)")
    private String label;

//    @Column(name = "isBooked", columnDefinition = "BOOLEAN")
//    private boolean isBooked;


    public TimeSlots(int id, LocalTime startTime, LocalTime endTime, String label) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.label = label;
    }

    public TimeSlots() {

    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public boolean isBooked(){
//        return false;
//    }
//
//    public void setIsBooked(boolean isBooked){
//        this.isBooked = isBooked;
//    }

}
