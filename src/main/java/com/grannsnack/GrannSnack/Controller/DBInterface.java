package com.grannsnack.GrannSnack.Controller;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DBInterface extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByUserName(String userName);
}
