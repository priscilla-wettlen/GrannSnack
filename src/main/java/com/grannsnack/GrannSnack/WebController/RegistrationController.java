package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import com.grannsnack.GrannSnack.Model.MyUser;
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
    public MyUser createUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        MyUser user = new MyUser();
        user.setUserName(name);
        user.setUserEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        if(user.getRole() == null) {
            user.setRole("USER");
        }
        return dbUserService.saveUser(user);
    }
}
