package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
@Table(name="COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment_content")
    @Lob
    private String commentContent;
    @Column(name = "comment_author_id", nullable = false)
    private int commentAuthorID;
    @Column(name = "created_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    @Column(name = "post_id", nullable = false)
    private int postID;

    public Comment(String commentContent, int commentAuthorID, int postID) {
        this.commentContent = commentContent;
        this.commentAuthorID = commentAuthorID;
        this.postID = postID;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public int getCommentID() {
        return id;
    }

    public void setCommentID(int commentID) {
        this.id = commentID;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentAuthorID() {
        return commentAuthorID;
    }

    public void setCommentAuthorID(int commentAuthorID) {
        this.commentAuthorID = commentAuthorID;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
