package com.grannsnack.GrannSnack.Web;

import com.grannsnack.GrannSnack.Controller.DBInterface;
import com.grannsnack.GrannSnack.Controller.DBService;
import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DBService dbService;

    @PostMapping("/register/user")
    public MyUser createUser(@RequestBody MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null) {
            user.setRole("USER");
        }
        return dbService.saveUser(user);
    }
    @GetMapping("/register/user")
    public String register() {
        return "User registry";
    }
}
