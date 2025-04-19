package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
