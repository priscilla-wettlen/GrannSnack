package com.grannsnack.GrannSnack.Web;

import com.grannsnack.GrannSnack.Controller.DBUserService;
import com.grannsnack.GrannSnack.Model.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

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
        return "redirect:/u/booking";
    }

    @PostMapping("/u/home/redirectToForum")
    public String redirectToForum() {
        return "redirect:/u/forum";
    }
}
