package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * This class is responsible for handling all communication with the database. It does this with a package called JpaRepositories which
 * takes the method head of each method and creates a suitable sql query.
 * @Author Joel Seger
 */
public interface DBForumInterface extends JpaRepository<Post, Integer> {

    /**
     * This method takes and id and finds a post by that id.
     * @param id the id of the post one wants to find
     * @return the post of the corresponding id
     */
    Post findPostById(int id);

    /**
     * This method finds and returns posts based on the author id provided.
     * @param postAuthorID the author id of the author which post one wants to return
     * @return the posts of the author with the corresponding author id
     */
    List<Post> findPostsByPostAuthorID(int postAuthorID);

    /**
     * This method is used to get the most recent posts with a limit. @Query is just a way to manually add the sql query to make sure it works as intended.
     * @param limit the imposed limit of how many post one wants to fetch
     * @return the posts
     */
    @Query(value = "SELECT p FROM Post p ORDER BY p.postDate DESC LIMIT :limit")
    List<Post> findPostsByOrderByPostDateDesc(@Param("limit") int limit);

    /**
     * This method takes all reported posts from the database and returns them.
     * @param reported a boolean to check whether a post is reported or not.
     * @return a list of reported posts
     */
    List<Post> findPostsByReported(boolean reported);

    /**
     * This method returns the very latest post.
     * @return a post.
     */
    Post findTopByOrderByPostDateDesc();

}
