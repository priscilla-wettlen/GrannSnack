package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.MyUser;
import com.grannsnack.GrannSnack.Model.Post;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DBForumInterface extends JpaRepository<Post, Integer> {

    Post findPostByPostDate(Date postDate);

    Post findPostById(int id);

    List<Post> findPostsByPostAuthorID(int postAuthorID);

    List<Post> findPostsByPostDateBetween(Date postDateAfter, Date postDateBefore);

    List<Post> findPostsByReported(boolean reported);
}
