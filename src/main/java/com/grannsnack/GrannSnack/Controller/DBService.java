package com.grannsnack.GrannSnack.Controller;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBService {

    @Autowired
    private DBInterface dbInterface;

    public DBService(DBInterface dbInterface) {
        this.dbInterface = dbInterface;
    }

    public Iterable<MyUser> getUsers() {
        return dbInterface.findAll();
    }

    public MyUser getUserById(Integer id) {
        return dbInterface.findById(id).get();
    }

    public MyUser saveUser(MyUser myUser) {
        return dbInterface.save(myUser);
    }

    public MyUser getUserByName(String name) {
        Optional<MyUser> user;
        if(name == null) {
            user = Optional.empty();
        } else {
            user = dbInterface.findByUserName(name);
        }
        return user.orElse(null);
    }

    public MyUser removeUser(Integer id) {
        if(!dbInterface.existsById(id)) {
            return null;
        }
        MyUser user = dbInterface.findById(id).get();
        dbInterface.deleteById(id);
        return user;
    }
}
