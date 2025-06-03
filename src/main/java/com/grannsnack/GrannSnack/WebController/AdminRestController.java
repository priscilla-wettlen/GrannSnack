package com.grannsnack.GrannSnack.WebController;
import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for handling all the endpoints for the admin pages. This class can show all the users in the system. An admin can make another user admin
 * or delete the user entirely. An admin can also see all the reported posts and delete them.
 */
@RestController
@RequestMapping("/a/home")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestController {
    private final DBUserService dbUserService;
    private final DBForumService dbForumService;

    public AdminRestController(DBUserService dbUserService, DBForumService dbForumService) {
        this.dbUserService = dbUserService;
        this.dbForumService = dbForumService;
    }

    /**
     * This method is used to retrieve all the users that have an account in the system. It retrieves the details of the current user so the
     * frontend can make sure admin cannot delete itself. This is to ensure that there is always one admin.
     * @param userDetails details of the currently logged-in user.
     * @return a List of maps containing a user and a boolean to check if the user is the current user.
     */
    @GetMapping("/users")
    public List<Map<String, Object>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails) {
        MyUser currentUser = dbUserService.getUserByEmail(userDetails.getUsername());
        List<MyUser> users = (List<MyUser>) dbUserService.getUsers();
        List<Map<String, Object>> result = new ArrayList<>();

        for(MyUser user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("isCurrentUser", user.equals(currentUser));
            result.add(map);
        }
        return result;
    }

    /**
     * This method is used to get a user based on a retrieved user id.
     * @param id the id of the user to be retrieved.
     * @return the user of the corresponding id.
     */
    @GetMapping("/users/{id}")
    public MyUser getUserById(@PathVariable Integer id) {
        if (id == null) {
            return null;

        }
        return dbUserService.getUserById(id);
    }

    /**
     * This method takes the id of a user to be deleted and removes all the user posts.
     * @param id the id of the user to be deleted.
     * @return a ResponseEntity telling the frontend how the operation went.
     */
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Integer id){
        MyUser userIdToDelete = dbUserService.removeUser(id);
        List<Post> userPosts = dbForumService.findPostByPostAuthorID(userIdToDelete.getId());

        if (userIdToDelete == null) {
            return ResponseEntity.notFound().build();
        }
        for(Post post : userPosts) {
            dbForumService.deletePostById(post.getPostId());
        }
        return ResponseEntity.ok("User with id " + id + " deleted successfully.");
    }

    /**
     * This method takes a user id and a boolean to change the current role of the user either from admin to user or back. It then sends back a ResponseEntity
     * telling the frontend how the operation went.
     * @param userId the user id of the user to change roles.
     * @param isAdmin the boolean value of either true or false. True if one is admin, false if one is not.
     * @return a ResponseEntity of how the operation went.
     */
    @PostMapping("/changeAdmin")
    public ResponseEntity<String> makeAdmin(@RequestParam int userId, @RequestParam boolean isAdmin) {
        MyUser user = dbUserService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if(isAdmin) {
            user.setRole("ADMIN,USER");
            dbUserService.saveUser(user);
            return ResponseEntity.ok("Admin status changed successfully.");
        } else {
            user.setRole("USER");
            dbUserService.saveUser(user);
            return ResponseEntity.ok("User status changed successfully.");
        }
    }

    /**
     * This method takes all the posts on the forum and returns the ones that have been reported to be shown in the frontend.
     * @return a list of posts that has been reported.
     */
    @GetMapping("/posts")
    public List<ForumDTO> getAllReportedPosts() {
        List<ForumDTO> postDTOs= new ArrayList<>();
        List<Post> posts;
        posts = dbForumService.findPostsByReported(true);
        for(Post post : posts) {
            postDTOs.add(new ForumDTO(post, dbUserService.getUserById(post.getPostAuthorID()), null ));
        }
        return postDTOs;
    }

    /**
     * This method is used for deleting a reported post on from the admin page. It takes the id of the post to be deleted and the deletes that post.
     * It returns a ResponseEntity to tell the frontend how the operation went.
     * @param postId the id of the post to be deleted.
     * @return a ResponseEntity of how the operation went.
     */
    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<String> deletePost (@PathVariable Integer postId){
       Post postToDelete = dbForumService.getPostById(postId);
       dbForumService.deletePostById(postToDelete.getPostId());
        return ResponseEntity.ok("Post with id " + postId + " deleted successfully.");
    }
}