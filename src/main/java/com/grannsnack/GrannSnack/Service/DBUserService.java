package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the main method for handling user related database actions. Here the database information is collected and saved or retrived and sent of
 * to somewhere els in the system.
 * @Author Joel Seger
 */
@Service
public class DBUserService {

    @Autowired
    private DBUserInterface dbUserInterface;

    private MyUser myUser;

    /**
     * This method is used to get all the current users in the system.
     * @return an Iterable of all the users currently in the system.
     */
    public Iterable<MyUser> getUsers() {
        return dbUserInterface.findAll();
    }

    /**
     * This method returns a user based on the id provided.
     * @param id the id of the user one wants to be returned
     * @return the user of the id.
     */
    public MyUser getUserById(Integer id) {
        return dbUserInterface.findById(id).get();
    }

    /**
     * This method saves a provided user to the database.
     * @param myUser the user object to be saved
     * @return the saved user object
     */
    public MyUser saveUser(MyUser myUser) {
        return dbUserInterface.save(myUser);
    }

    /**
     * This method returns a user by providing a username.
     * @param name the name of the user to return
     * @return the user of the provided username
     */
    public MyUser getUserByName(String name) {
        Optional<MyUser> user;
        if(name == null) {
            user = Optional.empty();
        } else {
            user = dbUserInterface.findByUserName(name);
        }
        return user.orElse(null);
    }

    /**
     * This method removes a user based on the provided id.
     * @param id the id of the user to be removed.
     * @return the user of the provided id.
     */
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

    /**
     * This method returns a user based on the email provided.
     * @param email the emial of the user to return
     * @return the user of with the email provided
     */
    public MyUser getUserByEmail(String email) {
        Optional<MyUser> user;
        if(email == null) {
            user = Optional.empty();
        } else {
            user = dbUserInterface.findByEmail(email);
        }
        return user.orElse(null);
    }

    /**
     * This method is responsible for creating a new user. It takes all the credentials needed and creates a new user then saving it to the database.
     * It returns true if the operation was successful and the new user could be found in the database and false if the user already exists or could not be found in the database
     * after creation.
     * @param userName the username of the user
     * @param email the email of the user
     * @param password the password of the user
     * @param role the roles of the user, always user until admin changes it.
     * @param asociation the association the users apartment is in
     * @param apartmentCode the apartment the user lives in.
     * @return a boolean true or false depending on if the user could be found in the database after creation.
     */
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

    /**
     * This method is responsible for checking if a user exists in the database or not.
     * @param user the user to be checked
     * @return a boolean true or false depending on whether the user is present in the database or not.
     */
    public boolean userIsPresent(MyUser user) {
        Optional<MyUser> olduser = dbUserInterface.findByUserName(user.getUserName());
        if(olduser.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
