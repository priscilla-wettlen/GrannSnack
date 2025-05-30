package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.Comment;
import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ForumRestController {

    @Autowired
    private DBForumService dbForumService;

    @Autowired
    private DBUserService dbUserService;

    @PostMapping("/u/forum/create")
    public ResponseEntity<String> posting(@RequestBody Post post , @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userEmail = userDetails.getUsername();
            MyUser user = dbUserService.getUserByEmail(userEmail);
            boolean ok = dbForumService.createPost(post.getPostTitle(), post.getPostContent(), user, false);

            if(ok) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Post successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post failed");
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating post: " + e.getMessage());
        }

    }

    @DeleteMapping("/u/forum/delete")
    public ResponseEntity<String> deleting(@RequestBody Post post) {
        Post newPost = dbForumService.getPostById(post.getPostId());

        if(newPost == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        } else {
            dbForumService.deletePostById(newPost.getPostId());
            return ResponseEntity.status(HttpStatus.OK).body("Post deleted");
        }
    }

    @GetMapping("/u/forum/posts")
    public ResponseEntity<List<ForumDTO>> fetchPosts(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer size,
                                                      Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }

        int currentPage = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 10;

        int limit = pageSize * (currentPage + 1);

        List<ForumDTO> posts = dbForumService.getRecentPosts(limit);

        //String UserEmail = authentication.getName();
        String userEmail = null;
        Object principle = authentication.getPrincipal();
        if(principle instanceof UserDetails) {
            userEmail = ((UserDetails)principle).getUsername();
        }

        for(ForumDTO post : posts) {
            if(post.getMyUser().getUserEmail().equals(userEmail)) {
                post.setIsUsers(true);
            } else {
                post.setIsUsers(false);
            }
        }

        if(posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
    }

    @PutMapping("/u/forum/edit-post")
    public ResponseEntity<String> editPost(@RequestBody Post post) {
        boolean ok = dbForumService.updatePost(post.getPostId(), post.getPostContent());
        if(ok) {
            return ResponseEntity.status(HttpStatus.OK).body("Post edited");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        }
    }

    @PostMapping("/u/forum/report")
    public ResponseEntity<String> report(@RequestParam("postId") int postId) {
        int newId = postId;
        boolean ok = dbForumService.reportPost(newId);

        if(ok) {
            return ResponseEntity.status(HttpStatus.OK).body("Post reported");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        }
    }

    @PostMapping("/u/forum/comment")
    public ResponseEntity<String> comment(@RequestBody Comment comment,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        System.out.println(userEmail);
        MyUser user = dbUserService.getUserByEmail(userEmail);

        boolean ok = dbForumService.createComment(comment.getCommentContent(), user.getUserName(), comment.getPostID());

        if(ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment failed");
        }
    }

}
