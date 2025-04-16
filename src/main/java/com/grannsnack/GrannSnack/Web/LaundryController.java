package com.grannsnack.GrannSnack.Web;

import com.grannsnack.GrannSnack.Model.LaundryManager;
import com.grannsnack.GrannSnack.Model.MyUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpResponse;

@Controller
public class LaundryController {

    @Autowired
    private LaundryManager laundryManager;

    @GetMapping("/u/booking")
    public String booking(Model model){
        Iterable<LaundryManager.LaundryTime> takenTimes = laundryManager.getTakenLaundryTimes();
        model.addAttribute("takenTimes", takenTimes);
        return "booking";
    }

    @PostMapping("/booking")
    public HttpStatus booking(@RequestParam int timeSlot, @RequestParam String laundryRoom, @RequestParam MyUser user, HttpServletResponse httpServletResponse){
        boolean success = laundryManager.addLaundryTime(timeSlot, laundryRoom, user);
        if(success){
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @GetMapping("/calender")
    public String calender() {
        return "calender";
    }
}
