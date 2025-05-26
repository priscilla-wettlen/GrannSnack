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

public interface DBForumInterface extends JpaRepository<Post, Integer> {

    Post findPostByPostDate(Date postDate);

    Post findPostById(int id);

    List<Post> findPostsByPostAuthorID(int postAuthorID);

    @Query(value = "SELECT p FROM Post p ORDER BY p.postDate DESC LIMIT :limit")
    List<Post> findPostsByOrderByPostDateDesc(@Param("limit") int limit);

    List<Post> findPostsByReported(boolean reported);

    Post findTopByOrderByPostDateDesc();

}
