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

/**
 * This class handles all the REST endpoints for the user forum page. It handles the creation of posts, editing of posts, deleting of posts,
 * also commenting and reporting posts.
 * @Author Joel Seger, Priscilla Wettlén
 */
@RestController
public class ForumRestController {

    @Autowired
    private DBForumService dbForumService;

    @Autowired
    private DBUserService dbUserService;

    /**
     * This method handles the creation of a new post. It takes in a Post object and returns a response entity with the status code of the
     * operation. If the operation was successful, it returns a CREATED status code, if not it returns a BAD_REQUEST status code.
     * @param post The post object to be created. It contains the title and content of the post.
     * @param userDetails The current user details from the system. It is used to get the user email and user id to create the post.
     * @return A response entity with the status code of the operation.
     */
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

    /**
     * This method handles the deletion of a post. It takes in a Post object and returns a response entity with the status code of the
     * operation. If the operation was successful, it returns a OK status code, if not it returns a BAD_REQUEST status code.
     * @param post The post object to be deleted. It contains the post id of the post to be deleted. The post id is used to find the post in the database.
     * @return A response entity with the status code of the operation.
     */
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

    /**
     * This method handles the fetching of all posts. It takes in an optional page and size parameters and returns a response entity with the status code of the
     * operation. If the operation was successful, it returns a OK status code, if not it returns a BAD_REQUEST status code.
     * @param page The page number to be fetched.
     * @param size The size of the page to be fetched.
     * @param authentication The current authentication object from the system. It is used to get the user email to check if the user is logged in and if the user is an admin.
     * @return A response entity with the status code of the operation.
     */
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

    /**
     * This method handles the fetching of a specific post. It takes in a Post object and returns a response entity with the status code of the
     * operation. If the operation was successful, it returns a OK status code, if not it returns a BAD_REQUEST status code.
     * @param post The post object to be fetched. It contains the post id of the post to be fetched.
     * @return A response entity with the status code of the operation.
     */
    @GetMapping("/u/forum/post")
    public ResponseEntity<ForumDTO> fetchPost(@RequestBody Post post) {}
    @PutMapping("/u/forum/edit-post")
    public ResponseEntity<String> editPost(@RequestBody Post post) {
        boolean ok = dbForumService.updatePost(post.getPostId(), post.getPostContent());
        if(ok) {
            return ResponseEntity.status(HttpStatus.OK).body("Post edited");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        }
    }

    /**
     * This method handles the reporting of a post. It takes in a post id and returns a response entity with the status code of the
     * operation. If the operation was successful, it returns a OK status code, if not it returns a BAD_REQUEST status code.
     * @param postId The post id of the post to be reported. It is used to find the post in the database. The post id is used to find the post in the database.
     * @return A response entity with the status code of the operation.
     */
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

    /**
     * This method handles the commenting on a post. It takes in a Comment object and returns a response entity with the status code of the
     * @param comment The comment object to be created. It contains the comment content and the post id of the post to which the comment is being made.
     * @param userDetails The current user details from the system. It is used to get the user email to create the comment. The user email is used to find the user in the database.
     * @return A response entity with the status code of the operation. If the operation was successful, it returns a CREATED status code, if not it returns a BAD_REQUEST status code.
     */
    @PostMapping("/u/forum/comment")
    public ResponseEntity<String> comment(@RequestBody Comment comment,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        MyUser user = dbUserService.getUserByEmail(userEmail);

        boolean ok = dbForumService.createComment(comment.getCommentContent(), user.getUserName(), comment.getPostID());

        if(ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Comment failed");
        }
    }

}
