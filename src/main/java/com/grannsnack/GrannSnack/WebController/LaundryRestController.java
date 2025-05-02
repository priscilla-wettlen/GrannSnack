package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.TimeSlots;
import com.grannsnack.GrannSnack.Service.DBLaundryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(">>>> HIT AVAILABILITY ENDPOINT <<<<");
        return dbLaundryService.getAvailableTimeSlots(date);
    }

    @PostMapping("/create")
    public String createBooking(
            @RequestParam("date") String dateString,
            @RequestParam("time_slot") int timeSlotId,
            @RequestParam(value = "notes", required = false) String notes) {

        LocalDate date = LocalDate.parse(dateString);
        dbLaundryService.createBooking(date, timeSlotId, notes);

        return "redirect:/u/laundry-booking?date=" + date;
    }

}
