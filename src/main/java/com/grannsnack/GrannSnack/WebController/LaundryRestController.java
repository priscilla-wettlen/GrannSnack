package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.TimeSlots;
import com.grannsnack.GrannSnack.Service.DBLaundryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DBLaundryService dbLaundryService;

    @GetMapping("/availability")
    public List<TimeSlots> getAvailability(@RequestParam LocalDate date) {
        System.out.println("Controller hit with date: " + date);
        return dbLaundryService.getAvailableTimeSlots(date);
    }

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

}
