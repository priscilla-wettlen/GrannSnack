package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for user details, which is a builtin system of springboot to handle different users in an application.
 * For context this class exists to load and get user information when logging in to verify. All other user-related processes is done in DBUserService.
 * @Author Joel Seger
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserInterface dbUserInterface;

    /**
     * This method loads a user by the email from the database. In this context the username is the users email. The reason it's called username is that springboot required it.
     * @param email the users email which springboot calles username.
     * @return a fully built user details object for the user.
     * @throws UsernameNotFoundException if the username is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<MyUser> user = dbUserInterface.findByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUserEmail())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
    }

    /**
     * This method gets the role of the user. It's used through out the system to check if the user can access different parts of the system or not.
     * @param user the user which is logged in or trying to logg in.
     * @return A string array containing either one or two strings. One if the user is a user or two if the user is an admin.
     */
    private String[] getRoles(MyUser user) {
        String role = user.getRole();
        if(role.isBlank()) {
            return new String[] {"USER"};
        }

        return role.split(",");
    }

    /**
     * This method gets the user id with email from the database.
     * @param email the users email.
     * @return an integer representing the users id and hence the user.
     */
    public Integer getUserIdByEmail(String email) {
        Optional<MyUser> user = dbUserInterface.findByEmail(email);
        if(user.isPresent()) {
            var userObj = user.get();
            return userObj.getId();
        }
        return null;
    }
}
