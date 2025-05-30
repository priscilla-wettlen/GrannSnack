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
public class DBUserService implements UserDetailsService {

    @Autowired
    private DBUserInterface dbUserInterface;

    private MyUser myUser;

    public DBUserService(DBUserInterface dbUserInterface) {
        this.dbUserInterface = dbUserInterface;
    }

    public Iterable<MyUser> getUsers() {
        return dbUserInterface.findAll();
    }

    public MyUser getUserById(Integer id) {
        return dbUserInterface.findById(id).get();
    }

    public MyUser saveUser(MyUser myUser) {
        return dbUserInterface.save(myUser);
    }

    public MyUser getUserByRole(String role) {return dbUserInterface.findByRole(role).get();}

    public MyUser getUserByName(String name) {
        Optional<MyUser> user;
        if(name == null) {
            user = Optional.empty();
        } else {
            user = dbUserInterface.findByUserName(name);
        }
        return user.orElse(null);
    }

    public MyUser removeUser(Integer id) {
        myUser = dbUserInterface.findById(id).get();
        if(myUser.getId().equals(id) && myUser.getRole().equals("ADMIN")) {
            System.out.println("Error: Admin cannot be removed");
            return myUser;
        }

        if(!dbUserInterface.existsById(id)) {
            return null;
        }
        MyUser user = dbUserInterface.findById(id).get();
        dbUserInterface.deleteById(id);
        return user;
    }

    public MyUser getUserByEmail(String email) {
        Optional<MyUser> user;
        if(email == null) {
            user = Optional.empty();
        } else {
            user = dbUserInterface.findByEmail(email);
        }
        return user.orElse(null);
    }

    public boolean createUser(String userName, String email, String password, String role, String asociation, String apartmentCode ) {
        Optional<MyUser> existingUsers = dbUserInterface.findByUserName(userName);
        Optional<MyUser> existingEmails = dbUserInterface.findByEmail(email);

        if(existingUsers.isPresent()) {
            return false;
        }

        if(existingEmails.isPresent()) {
            return false;
        }

        MyUser user = new MyUser();
        user.setUserName(userName);
        user.setUserEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setApartmentCode(apartmentCode);
        user.setAssociation(asociation);

        dbUserInterface.save(user);
        return true;
    }

    public boolean userIsPresent(MyUser user) {
        Optional<MyUser> olduser = dbUserInterface.findByUserName(user.getUserName());
        if(olduser.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

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

    public Integer getUserIdByEmail(String email) {
        Optional<MyUser> user = dbUserInterface.findByEmail(email);
        if(user.isPresent()) {
            var userObj = user.get();
            return userObj.getId();
        }
        return null;
    }
}
