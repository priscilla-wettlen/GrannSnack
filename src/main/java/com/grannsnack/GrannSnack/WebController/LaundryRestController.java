package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.Booking;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.TimeSlots;
import com.grannsnack.GrannSnack.Service.DBLaundryService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is responsible for handling all the endpoints for the laundry booking. It handles creating and deleting bookings. Also fetching all available products.
 */
@RestController
@RequestMapping("/u/laundry-booking")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class LaundryRestController {

    @Autowired
    private  DBLaundryService dbLaundryService;

    @Autowired
    private DBUserService dbUserService;

    /**
     * This method takes a date and retrieves all the current available timeslots for that day.
     * @param date the date one wants to retrieve the timeslots for.
     * @return returns a list of the available timeslot.
     */
    @GetMapping("/availability")
    public List<TimeSlots> getAvailability(@RequestParam LocalDate date) {
        return dbLaundryService.getAvailableTimeSlots(date);
    }

    /**
     * This method creates a new booking by retrieving the date and timeslot that one wishes to book, notes if any are written and the current user logged-in.
     * Then it checks if the current date is before the current date to make sure you can't book dates that have already been. Secoundly it checks that the user does not
     * have more than three bookings ahead of the current day. Lastly, it creates and saves the booking to the database.
     * @param date the day one wants to book.
     * @param timeSlot the timeslot of the day one wants to book.
     * @param notes a nonrequired string field used for whatever the user want.
     * @param userDetails details of the currently logged-in user.
     * @return returns a ResponseEntity to inform the frontend of how the backend handled it.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(
        @RequestParam("date") LocalDate date,
        @RequestParam("time_slot") int timeSlot,
        @RequestParam(value = "notes", required = false) String notes,
        @AuthenticationPrincipal UserDetails userDetails) {

        LocalDate today = LocalDate.now();

        if(date.isBefore(today)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kan inte boka datum som varit");
        }

        String userEmail = userDetails.getUsername();
        int userId = dbLaundryService.getUserIdByEmail(userEmail);
        MyUser user = dbUserService.getUserById(userId);
        if(dbLaundryService.getAllBookingsByUserId(user.getId()).size() >= 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Max antal tider bokade");
        }
        dbLaundryService.createBooking(date, timeSlot, notes, userId);

        return ResponseEntity.ok("Bokning av tvättid genomförd");
    }

    /**
     * This method returns all the currently logged-in users bookings ahead and including the current date.
     * @param userDetails details of the currently logged-in user.
     * @return a list of user bookings.
     */
    @GetMapping("/bookings")
    public List<Booking> getBookings(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Integer userId = dbUserService.getUserIdByEmail(email);
        return dbLaundryService.getAllBookingsByUserId(userId);
    }

    /**
     * This method takes a booking id and details of the currently logged-in user and then deletes the booking corresponding with the id.
     * @param id the id of the booking one wants to delete.
     * @param userDetails details of the currently logged-in user.
     * @return a ResponseEntity to inform the frontend how the operation went.
     */
    @DeleteMapping("/bookings/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        int userId = dbUserService.getUserIdByEmail(userDetails.getUsername());
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

