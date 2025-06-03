package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Service.DBUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The logincontroller i responsible for handling all endpoints connected to the login and registration pages.
 * It does this by leveraging the password encoder to encode passwords when a new user is created and the DBUserService
 * to create and save new users or handle returning users loggin in.
 * @Author Joel Seger
 */
@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DBUserService dbUserService;

    /**
     * This method is responsible for returning the login page once it's requested by the frontend.
     * @return the login.html file to be rendered by thymleaf.
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * This method is responsible for redirecting a newly login user to the right page. It it's an admin they get sent
     * to the admin home page and the user gets send to user home page.
     * @param request this HttpServletRequest is used to determine what role the logged in user has.
     * @return the method returns a redirect to which ever home the role should go to.
     */
    @GetMapping("/default")
    public String defaultPage(HttpServletRequest request) {
        if(request.isUserInRole("ADMIN")) {
            return "redirect:/a/home";
        }
        return "redirect:/u/home";
    }

    /**
     * This method is responsible for returning the registration page when the frontend request it.
     * @return the register.html file for thymleaf to render.
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * This method is responisble for taking the credentials of the new user when one is being created.
     * @param name the username of the user
     * @param email the email of the user later used to login
     * @param password the password of the user which will be BCrypt encoded
     * @param unit which assocciation the user is a part of.
     * @param apartment which apartment in the assocciation the user is living in.
     * @return returns a String if successful or a redirect to an error if the user could not be created.
     */
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
