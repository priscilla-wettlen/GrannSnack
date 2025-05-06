package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserInterface dbUserInterface;


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

    private String[] getRoles(MyUser user) {
        String role = user.getRole();
        if(role.isBlank()) {
            return new String[] {"USER"};
        }

        return role.split(",");
    }

}
