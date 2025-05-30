package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This class is responsible for communicating with the database regarding comments. It does this with a package called JpaRepositories which
 * takes the method head of each method and creates a suitable sql query.
 * @Author Joel Seger
 */
public interface DBCommentInterface extends JpaRepository<Comment, Integer> {

    /**
     * This method takes a post id and gets all the comments with that post id connected to it.
     * @param postId the post id of the post one want to get the comments from
     * @return a list of the comments on the post
     */
    List<Comment> findCommentsByPostID(int postId);

    /**
     * This method finds a specific comment by its id.
     * @param id the id of the comment.
     * @return the comment with the corresponding id.
     */
    Comment findCommentById(int id);
}
