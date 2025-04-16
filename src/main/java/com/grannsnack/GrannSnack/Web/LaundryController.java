package com.grannsnack.GrannSnack.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LaundryController {

    @GetMapping("/u/booking")
    public String booking(){
        return "booking";
    }
}
