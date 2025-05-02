package com.grannsnack.GrannSnack.WebController;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/u/laundry-booking")
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class LaundryController {

    @GetMapping
    public String showBookingCalendar() {

        return "booking-calender";
    }

}