package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This class is responsible for communicating directly with the database. JpaRepository is a package that turns method heads into queries
 * and then connects to the database to receive the information.
 * @Author Joel Seger
 */
public interface DBUserInterface extends JpaRepository<MyUser, Integer> {

    /**
     * This method tries to find a user using a username.
     * @param userName the username of the user one wants to find
     * @return an Optional<MyUser> which either could be one or not.
     */
    Optional<MyUser> findByUserName(String userName);

    /**
     * This method tries to find a user using an email.
     * @param email the email of the user one wants to find
     * @return an Optional<MyUser> which either could be one or not.
     */
    Optional<MyUser> findByEmail(String email);

    /**
     * This mehtod tries to find a user using the role of  the user.
     * @param role the role of the use one wants to find
     * @return an Optional<MyUser> which either could be one or not.
     */
    Optional<MyUser> findByRole(String role);
}
