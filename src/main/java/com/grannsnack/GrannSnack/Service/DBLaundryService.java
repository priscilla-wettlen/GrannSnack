package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;


import com.grannsnack.GrannSnack.Model.TimeSlots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DBLaundryService {

    @Autowired
    private DBLaundryInterface laundryInterface;

    @Autowired
    private DBTimeSlotsInterface timeSlotsInterface;

    @Autowired
    private DBUserService dbUserService;


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

    public Booking deleteBooking(int timeSlotId, int userId) {
        Optional<Booking> optionalBooking = laundryInterface.findById(timeSlotId);

        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();

            if (booking.getUserId() == userId) {
                laundryInterface.deleteById(timeSlotId);
            } else {
                throw new SecurityException("Error: Du får bara radera dina egna tider!");
            }
        } else {
            throw new IllegalArgumentException("Bokning hittades inte!.");
        }
        return optionalBooking.get();
    }

    public int getUserIdByEmail(String userEmail) {
        Integer userId = dbUserService.getUserIdByEmail(userEmail);
        if (userId == null) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }
        return userId;
    }

    public List<Booking> getAllBookingsByUserId(Integer userId) {
        return laundryInterface.findByUserId(userId);
    }
}