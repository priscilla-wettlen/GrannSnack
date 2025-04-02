package com.grupp10.grannsnack.Model;

import com.grupp10.grannsnack.Controller.DBInterface;
import com.grupp10.grannsnack.Controller.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private DBInterface db;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<MyUser> user = db.findByName(name);
        if(user.isPresent()) {
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getName())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException("User "+name+" is not found");
        }
    }

    private String[] getRoles(MyUser user) {
        String role = user.getRole();
        if(role == null || role.isBlank()) {
            return new String[] {"USER"};
        }

        return role.split(",");
    }
}
