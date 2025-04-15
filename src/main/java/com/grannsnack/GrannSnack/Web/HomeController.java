package com.grannsnack.GrannSnack.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String handleHome() {
        return "testprototyp";
    }

    @GetMapping("/a/home")
    public String handleAdminHome() {
        return "adminhome";
    }

    @GetMapping("/u/home")
    public String handleUserHome() {
        return "userhome";
    }

}
