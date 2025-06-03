package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This is the main method for handling user related database actions. Here the database information is collected and saved or retrived and sent of
 * to somewhere els in the system.
 * @Author Joel Seger
 */
@Service
public class DBUserService implements UserDetailsService {

    @Autowired
    private DBUserInterface dbUserInterface;

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
        MyUser user = dbUserInterface.findById(id).get();
        if(user.getId().equals(id) && user.getRole().equals("ADMIN")) {
            System.out.println("Error: Admin cannot be removed");
            return user;
        }

        if(!dbUserInterface.existsById(id)) {
            return null;
        }
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
     * This method loads a user by the user email. In spring security username is the field you use to log in. Since we want our
     * users to log in with email, we need to call email username and the other way around when using spring security methods which this is.
     * @param email the email of the user one wants to load.
     * @return the user details of the user with the corresponding email.
     * @throws UsernameNotFoundException if there is no user with that email, this error gets thrown.
     */
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

    /**
     * This method gets the roles of the user. It's used to confirm which sites a user is allowed to reach. For example, a user can't reach admin pages.
     * It does this by splitting the string with a comma to create two different roles. If a user has both roles they are considered admin. If not they are not admin.
     * @param user the user to check roles on.
     * @return a string array containing either one role or two roles.
     */
    private String[] getRoles(MyUser user) {
        String role = user.getRole();
        if(role.isBlank()) {
            return new String[] {"USER"};
        }

        return role.split(",");
    }

    /**
     * This method gets a user id by searching the database with an id.
     * @param email the email of the user one wants to find.
     * @return an integer representing the id of the user.
     */
    public Integer getUserIdByEmail(String email) {
        Optional<MyUser> user = dbUserInterface.findByEmail(email);
        if(user.isPresent()) {
            var userObj = user.get();
            return userObj.getId();
        }
        return null;
    }
}
