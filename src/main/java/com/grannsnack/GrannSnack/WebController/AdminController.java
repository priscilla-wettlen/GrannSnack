package com.grannsnack.GrannSnack.WebController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/a")
public class AdminController {

    @GetMapping("/forum")
    public String showPosts() {
        return "adminforum";
    }
}
