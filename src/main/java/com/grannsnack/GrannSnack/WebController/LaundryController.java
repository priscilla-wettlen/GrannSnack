package com.grannsnack.GrannSnack.WebController;


import com.grannsnack.GrannSnack.Service.DBLaundryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Controller
@RequestMapping("/u/laundry-booking")
public class LaundryController {

    @Autowired
    private DBLaundryService dbLaundryService;

    @GetMapping
    public String showBookingCalendar(
            @RequestParam(name = "week", required = false) Integer weekParam,
            @RequestParam(name = "year", required = false) Integer yearParam,
            Model model) {

        // If week or year not provided, use current date
        LocalDate now = LocalDate.now();
        int week = weekParam != null ? weekParam : now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int year = yearParam != null ? yearParam : now.getYear();

        // Get all bookings for the selected week
        Set<String> takenSlots = dbLaundryService.getTakenSlotsForWeek(week, year);

        // Get current user's bookings
        Set<String> userBookings = dbLaundryService.getUserBookingsForWeek(week, year);

        model.addAttribute("weekNumber", week);
        model.addAttribute("year", year);
        model.addAttribute("takenSlots", takenSlots);
        model.addAttribute("userBookings", userBookings);

        return "booking-calender";
    }

    @PostMapping("/create")
    public String createBooking(
            @RequestParam("day") int day,
            @RequestParam("slot") int slot,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "week", required = false) Integer week,
            @RequestParam(value = "year", required = false) Integer year) {

        // If week or year not provided, use current date
        LocalDate now = LocalDate.now();
        int bookingWeek = week != null ? week : now.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int bookingYear = year != null ? year : now.getYear();

        // Create and save booking
        dbLaundryService.createBooking(day, slot, notes, bookingWeek, bookingYear);

        // Redirect back to calendar view
        return "redirect:/u/laundry-booking?week=" + bookingWeek + "&year=" + bookingYear;
    }
}