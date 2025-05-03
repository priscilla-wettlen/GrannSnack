package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.TimeSlots;
import com.grannsnack.GrannSnack.Service.DBLaundryService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @PostMapping("/create")
//    public String createBooking(
//            @RequestParam("date") LocalDate date,
//            @RequestParam("time_slot") int timeSlot,
//            @RequestParam(value = "notes", required = false) String notes) {
//
//        dbLaundryService.createBooking(date, timeSlot, notes);
//
//        return "redirect:/u/laundry-booking?date=" + date;
//    }
@PostMapping("/create")
public ResponseEntity<Map<String, String>> createBooking(
        @RequestParam("date") LocalDate date,
        @RequestParam("time_slot") int timeSlot,
        @RequestParam(value = "notes", required = false) String notes){

    dbLaundryService.createBooking(date, timeSlot, notes);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Booking successful");
    response.put("url", "/u/laundry-booking");

    return ResponseEntity.ok(response);
}

}
