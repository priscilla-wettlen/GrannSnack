package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class ForumController {

    @Autowired
    private DBForumService dbForumService;

    @Autowired
    private DBUserService dbUserService;


    @GetMapping("/u/forum")
    public String forum() {
        return "forum";
    }

    @PostMapping("/u/forum/create")
    public ResponseEntity<String> posting(@RequestBody Post post , @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        MyUser user = dbUserService.getUserByEmail(userEmail);
        boolean ok = dbForumService.createPost(post.getPostTitle(), post.getPostContent(), user);

        if(ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Post successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post failed");
        }
    }

    @DeleteMapping("/u/forum/delete")
    public ResponseEntity<String> deleting(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        Post newPost = dbForumService.getPostById(post.getPostId());

        if(newPost == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        } else {
            dbForumService.deletePostById(newPost.getPostId());
            return ResponseEntity.status(HttpStatus.OK).body("Post deleted");
        }
    }

    @GetMapping("/u/forum/posts")
    @ResponseBody
    public ResponseEntity<List<Post>> fetchPosts(@RequestParam(required = false) Timestamp fromDate,
                                           @RequestParam(required = false) Timestamp toDate) {

        if(fromDate == null || toDate == null) {
            if(fromDate == null && toDate == null) {
                Timestamp now = new Timestamp(System.currentTimeMillis());

                fromDate = now;

                toDate = Timestamp.valueOf(now.toLocalDateTime().plusWeeks(1));
            }
            if(fromDate == null) {
                fromDate = Timestamp.valueOf(toDate.toLocalDateTime().minusWeeks(1));
            } else {
                toDate = Timestamp.valueOf(fromDate.toLocalDateTime().plusWeeks(1));
            }
        }

        List<Post> posts = dbForumService.getRecentPosts(fromDate, toDate);

        if(posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
    }

}
