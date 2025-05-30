package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
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


    public boolean createPost(String title, String content, MyUser user, boolean isReported) {
        Post post = new Post();
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPostAuthorID(user.getId());
        post.setPostDate(new Timestamp(System.currentTimeMillis()));
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


    public List<ForumDTO> getRecentPosts(int limit) {
        List<ForumDTO> postsDTO = new ArrayList<>();


        List<Post> posts = dbForumInterface.findPostsByOrderByPostDateDesc(limit);
        posts.sort((p1, p2) -> p2.getPostDate().compareTo(p1.getPostDate()));
        posts.sort((p1, p2) -> dbUserService.getUserById(p1.getPostAuthorID()).getRole().compareTo(dbUserService.getUserById(p2.getPostAuthorID()).getRole()));

        for(Post post : posts) {
            List<Comment> comments = dbCommentInterface.findCommentsByPostID(post.getPostId());
            comments.sort((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()));
            postsDTO.add(new ForumDTO(post, dbUserService.getUserById(post.getPostAuthorID()), comments));
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

    public Timestamp findLatestPostDate(){
        Post post = dbForumInterface.findTopByOrderByPostDateDesc();
        System.out.println(post.getPostDate());
        return post.getPostDate();
    }
  
    public List<Post> findPostsByReported(boolean reported) {
        return dbForumInterface.findPostsByReported(reported);
    }

    public List<Post> findPostByPostAuthorID(int postAuthotID) {
        return dbForumInterface.findPostsByPostAuthorID(postAuthotID);
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

    public boolean createComment(String commentContent, String commentAuthorName, int postID) {
        Comment comment = new Comment();
        comment.setCommentContent(commentContent);
        comment.setCommentAuthorName(commentAuthorName);
        comment.setPostID(postID);
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()) );
        dbCommentInterface.save(comment);

        Optional<Comment> newComment = Optional.ofNullable(dbCommentInterface.findCommentById(comment.getId()));
        return newComment.isPresent();
    }
}
