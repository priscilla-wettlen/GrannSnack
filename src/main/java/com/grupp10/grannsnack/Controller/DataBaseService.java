package com.grupp10.grannsnack.Controller;

import com.grupp10.grannsnack.Model.MyUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataBaseService {

    DBInterface db;

    public DataBaseService(DBInterface db) {
        this.db = db;
    }

    public Iterable<MyUser> getUsers() {
        return db.findAll();
    }

    public MyUser getUserByID(Integer id) {
        return db.findById(id).get();
    }

    public MyUser getUserByName(String name) {
        Optional<MyUser> user;
        if(name == null) {
            user = Optional.empty();
        } else {
            user = db.findByName(name);
        }
        return user.orElse(null);
    }

    public MyUser removeUser(Integer id) {
        if(!db.existsById(id)) {
            return null;
        }
        MyUser user = db.findById(id).get();
        db.deleteById(id);
        return user;
    }

    public MyUser saveUser(MyUser user) {
        db.save(user);
        return user;
    }
}
