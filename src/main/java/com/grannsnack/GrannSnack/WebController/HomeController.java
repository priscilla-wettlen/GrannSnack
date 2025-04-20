package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import com.grannsnack.GrannSnack.Model.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    //TODO Temporary fix so that we get redirecting to login when application starts. For testing purposes only.

        @GetMapping("/")
        public String home() {
            return "redirect:/login"; // or return a view like "home"
        }

    //TODO End of TODO

    @Autowired
    private DBUserService userDB;


    @GetMapping("/home")
    public String handleHome() {
        return "testprototyp";
    }

    @GetMapping("/a/home")
    public String handleAdminHome() {
        return "adminhome";
    }

    @GetMapping("/u/home")
    public String handleUserHome(HttpServletRequest request, Model model) {
        String name = request.getUserPrincipal().getName();
        MyUser user = userDB.getUserByName(name);
        model.addAttribute("user", user);
        return "userhome";
    }

    @PostMapping("/u/home/redirectToLaundry")
    public String redirectToLaundryBooking() {
        return "redirect:/u/laundry-booking";
    }

    @PostMapping("/u/home/redirectToForum")
    public String redirectToForum() {
        return "redirect:/u/forum";
    }


}
