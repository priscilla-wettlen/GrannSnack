package com.grannsnack.GrannSnack.WebController;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/a/home")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final DBUserService dbUserService;
    private final DBForumService dbForumService;

    public AdminController(DBUserService dbUserService, DBForumService dbForumService) {
        this.dbUserService = dbUserService;
        this.dbForumService = dbForumService;
    }

    @GetMapping("/users")
    public List<MyUser> getAllUsers(Integer userId) {
        List<MyUser> users = new ArrayList<>();
        if (userId != null) {
            MyUser user = dbUserService.getUserById(userId);
            if (user != null && !user.getRole().equals("ADMIN")) {
                users.add(user);
            } //else {
//                for (MyUser u : dbUserService.getUsers()) {
//                    if (!u.getRole().equals("ADMIN")) {
//                        users.add(u);
//                    }
//                }
//            }
        }
        return (List<MyUser>) dbUserService.getUsers();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Integer id){
        MyUser userIdToDelete = dbUserService.removeUser(id);
        if (userIdToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("User with id " + id + " deleted successfully.");
    }
}

