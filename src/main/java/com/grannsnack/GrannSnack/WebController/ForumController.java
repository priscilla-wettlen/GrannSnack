package com.grannsnack.GrannSnack.WebController;

import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import com.grannsnack.GrannSnack.Service.DBForumService;
import com.grannsnack.GrannSnack.Service.DBUserService;
import com.grannsnack.GrannSnack.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
    public ResponseEntity<String> posting(@RequestBody Post post, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        MyUser user = dbUserService.getUserByEmail(userEmail);
        boolean ok = dbForumService.createPost(post.getPostTitle(), post.getPostContent(), user);

        if(ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Post successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post failed");
        }
    }

}
