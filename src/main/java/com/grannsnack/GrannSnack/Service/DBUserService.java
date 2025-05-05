package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class DBUserService {

    @Autowired
    private DBUserInterface dbUserInterface;

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

    public boolean createUser(String userName, String email, String password, String role) {
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

        dbUserInterface.save(user);
        return true;
    }
}
