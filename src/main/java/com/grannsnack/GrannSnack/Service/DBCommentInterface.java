package com.grannsnack.GrannSnack.Service;

import com.grannsnack.GrannSnack.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBCommentInterface extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentsByPostID(int postId);

    Comment findCommentById(int id);
}
