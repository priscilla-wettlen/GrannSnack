package com.grupp10.grannsnack.Web;

import com.grupp10.grannsnack.Controller.DataBaseService;
import com.grupp10.grannsnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private final DataBaseService dbs;

    public WebController(DataBaseService dbs) {
        this.dbs = dbs;
    }

    @GetMapping("/home")
    public String helloWorld() {
        return "home";
    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "home_user";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome() {
        return "home_admin";
    }

    @GetMapping("/user/getUsers")
    public String listNB(Model model) {
        Iterable<MyUser> users = dbs.getUsers();
        model.addAttribute("users", users);
        return "display_users";
    }

}
