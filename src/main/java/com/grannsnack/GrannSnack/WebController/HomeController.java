package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import com.grannsnack.GrannSnack.Model.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private DBUserService userDB;

    @GetMapping("/")
    public String handleRoot() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String handleHome() {
        return "index";
    }

    @GetMapping("/a/home")
    public String handleAdminHome() {
        return "adminhome";
    }

    @GetMapping("/a/forum")
    public String handleAdminForum() {
        return "adminforum";
    }

    @GetMapping("/u/home")
    public String handleUserHome(HttpServletRequest request, Model model) {
        String name = request.getUserPrincipal().getName();
        MyUser user = userDB.getUserByName(name);
        model.addAttribute("user", user);
        return "userhome";
    }

    @GetMapping("/u/profile")
    public String handleUserProfile(HttpServletRequest request, Model model) {
        String name = request.getUserPrincipal().getName();
        MyUser user = userDB.getUserByEmail(name); //because it's using the email as the name, for some weird reason
        System.out.println("Logged-in user name: " + name);
        System.out.println("Loaded user: " + user);
        model.addAttribute("user", user);
        return "userprofile";
    }

    @PostMapping("/u/profile/edit")
    public ResponseEntity<String> editProfile(@RequestParam String userName, String email, @AuthenticationPrincipal UserDetails userDetails) {
        boolean success = userDB.updateUser(userName, email);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("User profile successfully edited");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name or email not found");
        }
    }

    @GetMapping("/u/laundry-booking")
    @CrossOrigin(origins = "http://127.0.0.1:8080")
    public String showBookingCalendar() {
        return "booking-calender";
    }

}
