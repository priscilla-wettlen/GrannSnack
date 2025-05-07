package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DBForumService {

    @Autowired
    private  DBForumInterface dbForumInterface;

    @Autowired
    private  DBUserService dbUserService;

    private Timestamp date;

    public boolean createPost(String title, String content, MyUser user) {
        Post post = new Post();
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostAuthorID(user.getId());
        post.setPostDate(date = new Timestamp(System.currentTimeMillis()));

        dbForumInterface.save(post);
        Optional<Post> newPost = Optional.ofNullable(dbForumInterface.findPostById(post.getPostId()));
        return newPost.isPresent();
    }

    public Post getPostById(int id) {
        return dbForumInterface.findPostById(id);
    }

    public void deletePostById(int id) {
        dbForumInterface.deleteById(id);
    }

    public List<ForumDTO> getRecentPosts(Timestamp dateAfter, Timestamp dateBefore) {
        List<ForumDTO> postsDTO = new ArrayList<>();

        Date after = Date.valueOf(dateAfter.toLocalDateTime().toLocalDate());
        Date before = Date.valueOf(dateBefore.toLocalDateTime().toLocalDate());

        List<Post> posts = dbForumInterface.findPostsByPostDateBetween(after, before);
        posts.sort((p1, p2) -> p2.getPostDate().compareTo(p1.getPostDate()));

        for(Post post : posts) {
            postsDTO.add(new ForumDTO(post, dbUserService.getUserById(post.getPostAuthorID()).getUserName()));
        }

        return postsDTO;
    }

    public Timestamp getDate() {
        return date = new Timestamp(System.currentTimeMillis());
    }
}
