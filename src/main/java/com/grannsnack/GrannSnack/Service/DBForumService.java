package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
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
    @Autowired
    private  DBCommentInterface dbCommentInterface;

    private Timestamp date;

    public boolean createPost(String title, String content, MyUser user, boolean isReported) {
        Post post = new Post();
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostAuthorID(user.getId());
        post.setPostDate(date = new Timestamp(System.currentTimeMillis()));
        post.setReported(isReported);

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
            postsDTO.add(new ForumDTO(post, dbUserService.getUserById(post.getPostAuthorID()), dbCommentInterface.findCommentsByPostID(post.getPostId())));
        }

        return postsDTO;
    }

    public boolean updatePost(int postId, String content) {
        Optional<Post> optionalPost = dbForumInterface.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setPostContent(content);
            dbForumInterface.save(post);
            return true;
        } else {
            return false;
        }
    }
  
    public List<Post> findPostsByReported(boolean reported) {
        return dbForumInterface.findPostsByReported(reported);
    }

    public List<Post> findPostByPostAuthorID(int postAuthotID) {
        return dbForumInterface.findPostsByPostAuthorID(postAuthotID);
    }

    public Timestamp getDate() {
        return date = new Timestamp(System.currentTimeMillis());
    }

    public boolean reportPost(int postId) {
        Optional<Post> optionalPost = dbForumInterface.findById(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setReported(true);
            dbForumInterface.save(post);
            return true;
        } else {
            return false;
        }
    }

    public boolean createComment(String commentContent, String commentAurthorName, int postID) {
        Comment comment = new Comment(commentContent, commentAurthorName, postID);
        dbCommentInterface.save(comment);

        Optional<Comment> newComment = Optional.ofNullable(dbCommentInterface.findCommentById(comment.getCommentID()));
        return newComment.isPresent();
    }
}
