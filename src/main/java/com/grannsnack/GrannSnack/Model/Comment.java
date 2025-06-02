package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * This class represents a comment in the system. It does this by using the Entity annotation. This makes the class
 * work automatically with our database. Creating a table but also getting the comment from the database as objects without
 * the extra work of parsing.
 *
 * It does this by having annotations for every instance variable. The @Column annotation tells spring boot and jpa which
 * column the information is part of.
 *
 * The @Id annotation tells spring that the value is to be treated as an Id, which is usually a primary key.
 * The @GeneratedValue annotation makes sure that the value is ever-increasing.
 *
 * The @Lob annotation meaning Large OBject. This is because SQL does not support Strings. Instead, it uses varchar, which is limited to 255
 * characters. To not limit our comments to 255 characters we use the annotation @Lob which makes the variable a LONGTEXT in sql istead.
 *
 * @Author Joel Seger
 */
@Entity
@Table(name="COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment_content")
    @Lob
    private String commentContent;
    @Column(name = "comment_author_name", nullable = false)
    private String commentAuthorName;
    @Column(name = "created_at", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    @Column(name = "post_id", nullable = false)
    private int postID;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentAuthorName() {
        return commentAuthorName;
    }

    public void setCommentAuthorName(String commentAuthorName) {
        this.commentAuthorName = commentAuthorName;
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
