package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.Booking;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.TimeSlots;
import com.grannsnack.GrannSnack.Service.DBLaundryService;
import com.grannsnack.GrannSnack.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/u/laundry-booking")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class LaundryRestController {

    @Autowired
    private  DBLaundryService dbLaundryService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /*
     * Check available time slots
     * */

    @GetMapping("/availability")
    public List<TimeSlots> getAvailability(@RequestParam LocalDate date) {
        return dbLaundryService.getAvailableTimeSlots(date);
    }

    /*
     * Create a booking
     * */

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createBooking(
        @RequestParam("date") LocalDate date,
        @RequestParam("time_slot") int timeSlot,
        @RequestParam(value = "notes", required = false) String notes,
        @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        int userId = dbLaundryService.getUserIdByEmail(userEmail);
        dbLaundryService.createBooking(date, timeSlot, notes, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking successful");
        response.put("url", "/u/laundry-booking");

        return ResponseEntity.ok(response);

    }

    /*
    * Endpoint to see a specific user's bookings. Not yet mapped on frontend
    * */

    @GetMapping("/bookings")
    public List<Booking> getBookings(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Integer userId = myUserDetailsService.getUserIdByEmail(email);
        return dbLaundryService.getAllBookingsByUserId(userId);
    }

    @DeleteMapping("/bookings/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = myUserDetailsService.getUserIdByEmail(userDetails.getUsername());
        try {
            Booking bookingIdToDelete = dbLaundryService.deleteBooking(id, userId);
            if (bookingIdToDelete == null) {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

}

