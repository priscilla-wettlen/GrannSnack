package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;


import com.grannsnack.GrannSnack.Model.TimeSlots;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DBLaundryService {
    private final DBLaundryInterface laundryInterface;
    private final DBTimeSlotsInterface timeSlotsInterface;
    private final MyUserDetailsService userDetailsService;


    public DBLaundryService(DBLaundryInterface laundryInterface, DBTimeSlotsInterface timeSlotsInterface, MyUserDetailsService userDetailsService) {
        this.laundryInterface = laundryInterface;
        this.timeSlotsInterface = timeSlotsInterface;
        this.userDetailsService = userDetailsService;
    }

    public List<TimeSlots> getAvailableTimeSlots(LocalDate date) {
        List<TimeSlots> allTimeSlots = timeSlotsInterface.findAll();
        List<Booking> bookedTimeSlots = laundryInterface.findByDate(date);

        Set<Integer> bookedIds = bookedTimeSlots.stream()
                .map(b -> b.getTimeSlot().getId())
                .collect(Collectors.toSet());

        return allTimeSlots.stream()
                .filter(slot -> !bookedIds.contains(slot.getId()))
                .collect(Collectors.toList());
    }

    public void createBooking(LocalDate date, int timeSlotId, String notes, int userId) {
        Booking booking = new Booking();
        booking.setDate(date);
        booking.setTimeSlot(timeSlotsInterface.findById(timeSlotId).orElseThrow());
        booking.setNotes(notes);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUserId(userId);
        laundryInterface.save(booking);
    }

    public int getUserIdByEmail(String userEmail) {
        Integer userId = userDetailsService.getUserIdByEmail(userEmail);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }
        return userId;
    }

    public List<Booking> getAllBookingsByUserId(Integer userId) {
        return laundryInterface.findByUserId(userId);
    }

//    public List<Booking> getAllBookedTimeSlotsByDateExceptUser(LocalDate date, int currentUserId) {
//        List<Booking> bookedTimeSlots = laundryInterface.findByDate(date);
//
//        return bookedTimeSlots.stream()
//                .filter(booking -> booking.getUserId() != currentUserId)
//                .collect(Collectors.toList());
//    }
}