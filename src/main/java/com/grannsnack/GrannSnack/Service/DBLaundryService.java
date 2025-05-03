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


    public DBLaundryService(DBLaundryInterface laundryInterface, DBTimeSlotsInterface timeSlotsInterface) {
        this.laundryInterface = laundryInterface;
        this.timeSlotsInterface = timeSlotsInterface;
    }

    public List<TimeSlots> getAvailableTimeSlots(LocalDate date) {
        List<TimeSlots> allTimeSlots = timeSlotsInterface.findAll();
        List<Booking> bookedTimeSlots = laundryInterface.findByDate(date);


//     @Autowired
//     private MyUserDetailsService userDetailsService;

//     public Set<String> getTakenSlotsForMonth(int month, int year) {
//         List<Booking> bookings = dbLaundryInterface.findByMonthAndYear(month, year);
//         Set<String> takenSlots = new HashSet<>();

//         for (Booking booking : bookings) {
//             takenSlots.add(booking.getSlotKey());
//         }

//         return takenSlots;
//     }

//     public Set<String> getUserBookingsForWeek(int month, int year) {
//         int currentUserId = getCurrentUserId();
//         List<Booking> userBookings = dbLaundryInterface.findByUserIdAndMonthAndYear(
//                 currentUserId, month, year);


        Set<Integer> bookedIds = bookedTimeSlots.stream()
                .map(b -> b.getTimeSlot().getId())
                .collect(Collectors.toSet());

        return allTimeSlots.stream()
                .filter(slot -> !bookedIds.contains(slot.getId()))
                .collect(Collectors.toList());
    }

    public void createBooking(LocalDate date, int timeSlotId, String notes) {
        Booking booking = new Booking();
        booking.setDate(date);
        booking.setTimeSlot(timeSlotsInterface.findById(timeSlotId).orElseThrow());

        booking.setNotes(notes);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUserId(booking.getUserId());
        laundryInterface.save(booking);
    }

}