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

/**
 * The HomeController is responsible for handling all the home paths and url:S. It handles the index page, both the user and admin home page
 * and the redirects to forum and laundry booking. It's done this way because now we can have all the redirects in one or two controllers and the
 * other controllers can be rest controllers meaning they only serve information and not HTML files.
 * @Author Joel Seger, Priscilla Wettlén
 */
@Controller
public class HomeController {

    @Autowired
    private DBUserService userDB;

    /**
     * This method handle the root. If a user where to type the url / they would come to the index site.
     * @return a redirect to the idex page.
     */
    @GetMapping("/")
    public String handleRoot() {
        return "redirect:/home";
    }

    /**
     * This method handle the home url. It returns the index page.
     * @return the index.html
     */
    @GetMapping("/home")
    public String handleHome() {
        return "index";
    }

    /**
     * This method handle the admin home page.
     * @return the adminhome.html
     */
    @GetMapping("/a/home")
    public String handleAdminHome() {
        return "adminhome";
    }

    /**
     * This method handles the admin forum page.
     * @return the adminforum.html
     */
    @GetMapping("/a/forum")
    public String handleAdminForum() {
        return "adminforum";
    }

    /**
     * This method handles the user home page. It sends the users name back to the fontend to be displayed.
     * @param request the current user details from the system
     * @param model a model to send information back to the frontend through
     * @return the userhome.html
     */
    @GetMapping("/u/home")
    public String handleUserHome(HttpServletRequest request, Model model) {
        String name = request.getUserPrincipal().getName();
        MyUser user = userDB.getUserByName(name);
        model.addAttribute("user", user);
        return "userhome";
    }

    /**
     * This method handles the user profile page. It sends the user back to the frontend to be displayed.
     * @param request the current user details from the system
     * @param model a model to send information back to the frontend through.
     * @return the userprofile.html
     */
    @GetMapping("/u/profile")
    public String handleUserProfile(HttpServletRequest request, Model model) {
        String name = request.getUserPrincipal().getName();
        MyUser user = userDB.getUserByEmail(name); //because it's using the email as the name, for some weird reason
        model.addAttribute("user", user);
        return "userprofile";
    }

    /**
     * This method handles saving edited information on the user profile. It takes the credentials from the frontend and saves it to the database.
     * @param name the username of the user
     * @param email the users email
     * @param userDetails the current logged in user from the system
     * @return a ResponeEntity containing a string. Either HttpsStatus OK if successful or BAD_REQUEST if unsuccessful.
     */
    @PostMapping("/u/profile/edit")
    public ResponseEntity<String> editProfile(@RequestParam String name, @RequestParam String email, @AuthenticationPrincipal UserDetails userDetails) {
        String oldemail = userDetails.getUsername();
        MyUser currentUser = userDB.getUserByEmail(oldemail);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nuvarande användare hittades inte");
        }

        if (!email.equals(currentUser.getUserEmail())) {
            MyUser existingEmail = userDB.getUserByEmail(email);
            if (existingEmail != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Den email:en används inte");
            }
        }

        if (!name.equals(currentUser.getUserName())) {
            MyUser existingName = userDB.getUserByName(name);
            if (existingName != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Det namnet används redan.");
            }
        }

        currentUser.setUserName(name);
        currentUser.setUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body("Användarprofil uppdaterad");
    }


    /**
     * This method handles redirect and fetching of the laundry booking html
     * @return the laundry booking html file.
     */
    @GetMapping("/u/laundry-booking")
    @CrossOrigin(origins = "http://127.0.0.1:8080")
    public String showBookingCalendar() {
        return "booking-calender";
    }

    @GetMapping("/u/forum")
    public String forum() {
        return "forum";
    }
}
