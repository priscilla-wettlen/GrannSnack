package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DBUserService dbUserService;


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String unit, @RequestParam String apartment) {
        boolean isCreated = dbUserService.createUser(name, email, passwordEncoder.encode(password), "USER", unit, apartment);

        if(!isCreated) {
            return "redirect:/register?error=true";
        } else {
            return "successful";
        }
    }
}
