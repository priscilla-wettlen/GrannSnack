package com.grupp10.grannsnack.Controller;

import com.grupp10.grannsnack.Model.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DBInterface extends CrudRepository<MyUser, Integer> {
    Optional<MyUser> findByName(String name);
}
