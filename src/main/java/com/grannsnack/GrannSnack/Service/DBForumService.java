package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class DBForumService {

    @Autowired
    private  DBForumInterface dbForumInterface;

    @Autowired
    private  DBUserService dbUserService;

    private final Date date = Date.valueOf(LocalDate.now());

    public boolean createPost(String title, String content, MyUser user) {
        Post post = new Post();
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostAuthorID(user.getId());
        post.setPostDate(date);

        dbForumInterface.save(post);
        Optional<Post> newPost = Optional.ofNullable(dbForumInterface.findPostById(post.getPostId()));
        return newPost.isPresent();
    }
}
