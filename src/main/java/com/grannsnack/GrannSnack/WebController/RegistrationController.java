package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("DBUserService")
    @Autowired
    private DBUserService dbUserService;


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        boolean isCreated = dbUserService.createUser(name, email, passwordEncoder.encode(password), "USER");

        if(!isCreated) {
            return "redirect:/register?error=true";
        } else {
            return "redirect:/login";
        }
    }
}
