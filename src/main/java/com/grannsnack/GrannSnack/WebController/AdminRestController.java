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

    @GetMapping("/users/{id}")
    public MyUser getUserById(@PathVariable Integer id) {
        if (id != null) {
            return null;

        }
        return dbUserService.getUserById(id);
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

    @DeleteMapping("/delete-post/{postId}")
    public ResponseEntity<String> deletePost (@PathVariable Integer postId){
       Post postToDelete = dbForumService.getPostById(postId);
       dbForumService.deletePostById(postToDelete.getPostId());
        return ResponseEntity.ok("Post with id " + postId + " deleted successfully.");
    }
}

