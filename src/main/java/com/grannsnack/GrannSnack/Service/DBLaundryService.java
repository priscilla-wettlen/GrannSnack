package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DBLaundryService {

    @Autowired
    private DBLaundryInterface dbLaundryInterface;

    @Autowired
    private MyUserDetailsService userDetailsService;

    public Set<String> getTakenSlotsForMonth(int month, int year) {
        List<Booking> bookings = dbLaundryInterface.findByMonthAndYear(month, year);
        Set<String> takenSlots = new HashSet<>();

        for (Booking booking : bookings) {
            takenSlots.add(booking.getSlotKey());
        }

        return takenSlots;
    }

    public Set<String> getUserBookingsForWeek(int month, int year) {
        int currentUserId = getCurrentUserId();
        List<Booking> userBookings = dbLaundryInterface.findByUserIdAndMonthAndYear(
                currentUserId, month, year);

        Set<String> userSlots = new HashSet<>();
        for (Booking booking : userBookings) {
            userSlots.add(booking.getSlotKey());
        }

        return userSlots;
    }

    public void createBooking(int day, int slot, String notes, int month, int year) {
        List<Booking> existingBookings = dbLaundryInterface.findByDayAndTimeSlotAndMonthAndYear(
                day, slot, month, year);

        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("This time slot is already booked");
        }

        Booking booking = new Booking();
        booking.setDay(day);
        booking.setTimeSlot(slot);
        booking.setMonth(month);
        booking.setYear(year);
        booking.setNotes(notes);
        booking.setUserId(getCurrentUserId());

        dbLaundryInterface.save(booking);
    }

    private int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDetailsService.getUserIdByUsername(authentication.getName());
    }
}