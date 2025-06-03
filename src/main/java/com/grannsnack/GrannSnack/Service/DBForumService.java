package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
import com.grannsnack.GrannSnack.Model.ForumDTO;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * This class works as a bridge between the database and the rest of the application. It gets the data from the database and repacks it in the right format.
 * It handles many different requests but generally works by taking data from a database and then reconfigures it in some way before sending it towards the rest of the program.
 */
@Service
public class DBForumService {

    @Autowired
    private  DBForumInterface dbForumInterface;
    @Autowired
    private  DBUserService dbUserService;
    @Autowired
    private  DBCommentInterface dbCommentInterface;

    /**
     * This method is responsible for creating a post. It takes the title, content, the user publishing it and a boolean if the post is reported or not.
     * @param title The title of the post
     * @param content the content of the post generally a long string.
     * @param user the user who posted.
     * @param isReported a boolean if the post is reported or not. Always false to begin with.
     * @return a boolean that represents if the operation was completed or not.
     */
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

    /**
     * This method queries the database for a post using an id.
     * @param id the id of the post one wants to find.
     * @return the post with the corresponding id.
     */
    public Post getPostById(int id) {
        return dbForumInterface.findPostById(id);
    }

    /**
     * This method is used to delete a post based on the id provided.
     * @param id the id of the post to delete.
     */
    public void deletePostById(int id) {
        dbForumInterface.deleteById(id);
    }


    /**
     * This method is used to get the most recent posts up unto a decided limit.
     * @param limit the limit of posts one wants
     * @return a List of ForumDTO.
     */
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

    /**
     * This method is used to update a current post. It takes the post id and content and applies it before saving the post again.
     * @param postId the id of the post to update
     * @param content the new content of the post
     * @return a boolean to represent if the operation was completed or not.
     */
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

    /**
     * This method is used to get all the reported posts.
     * @param reported a boolean representing if a post if repoted or not.
     * @return A list of reported Posts
     */
    public List<Post> findPostsByReported(boolean reported) {
        return dbForumInterface.findPostsByReported(reported);
    }

    /**
     * This method is used to find a post based on the id of the author.
     * @param postAuthotID the aurhot id of the post one wants to find.
     * @return a list of all the post a specific author has made
     */
    public List<Post> findPostByPostAuthorID(int postAuthotID) {
        return dbForumInterface.findPostsByPostAuthorID(postAuthotID);
    }

    /**
     * This method is used to report a post. It takes the post id and then changes the boolean isReported of that post to true.
     * @param postId the id of the post to report
     * @return a boolean indicating if the operation was a succress or not.
     */
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

    /**
     * This method is used to create a comment. It takes the comment content, the name of the author, and the post id of the post the comment is added to.
     * @param commentContent the contet of the comment, usually a long string.
     * @param commentAuthorName the name of the user creating the comment
     * @param postID the id of the post where the comment is created
     * @return a boolean representing if the operation was a success or not.
     */
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
