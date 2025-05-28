package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBUserInterface extends JpaRepository<MyUser, Integer> {
    Optional<MyUser> findByUserName(String userName);

    Optional<MyUser> findByEmail(String email);

    Optional<MyUser> findByRole(String role);
}
