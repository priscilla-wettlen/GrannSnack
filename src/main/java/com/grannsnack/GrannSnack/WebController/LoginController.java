package com.grannsnack.GrannSnack.WebController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/default")
    public String defaultPage(HttpServletRequest request) {
        if(request.isUserInRole("ADMIN")) {
            return "redirect:/a/home";
        }
        return "redirect:/u/home";
    }
}
