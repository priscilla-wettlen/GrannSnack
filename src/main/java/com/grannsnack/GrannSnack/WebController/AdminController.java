package com.grannsnack.GrannSnack.WebController;
import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import com.grannsnack.GrannSnack.Service.DBForumInterface;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
            }
        }
        return (List<MyUser>) dbUserService.getUsers();
    }


    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Integer id){
        MyUser userIdToDelete = dbUserService.removeUser(id);
        List<Post> userPosts = dbForumService.findPostByPostAuthorID(userIdToDelete.getId());

        for(Post post : userPosts) {
            dbForumService.deletePostById(post.getPostId());
        }
        if (userIdToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("User with id " + id + " deleted successfully.");
    }

    @GetMapping("/posts")
    public List<Post> getAllReportedPosts() {
        List<Post> posts;
        posts = dbForumService.findPostsByReported(false);
        return posts;
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost (@PathVariable Integer postId){
       Post postToDelete = dbForumService.getPostById(postId);
       dbForumService.deletePostById(postToDelete.getPostId());
        return ResponseEntity.ok("Post with id " + postId + " deleted successfully.");
    }
}

