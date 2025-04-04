package com.grannsnack.GrannSnack.Web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String handleHome() {
        return "Welcome to Grann Snack Home page!";
    }
}
