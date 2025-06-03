package com.grannsnack.GrannSnack.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * This class represents a post in the system. It does this by using the Entity annotation. This makes the class
 * work automatically with our database. Creating a table but also getting the post from the database as objects without
 * the extra work of parsing.
 * <p>
 * It does this by having annotations for every instance variable. The @Column annotation tells spring boot and jpa which
 * column the information is part of.
 * <p>
 * The @Id annotation tells spring that the value is to be treated as an Id, which is usually a primary key.
 * The @GeneratedValue annotation makes sure that the value is ever-increasing.
 * <p>
 * The @Temporal annotation defines that the postDate variable is a timestamp.
 *
 * @Author Joel Seger
 */
@Entity
@Table(name="POST")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "post_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp postDate;
    @Column(name = "post_author_id", columnDefinition = "INT NOT NULL")
    private int postAuthorID;
    @Column(name = "post_title", columnDefinition = "VARCHAR(255)")
    private String postTitle;
    @Column(name = "post_content", columnDefinition = "LONGTEXT")
    private String postContent;
    @Column(name = "post_image", columnDefinition = "VARCHAR(255)")
    private String postImage;
    @Column(name = "is_reported", columnDefinition = "BOOLEAN")
    private boolean reported = false;

    public int getPostId() {
        return id;
    }

    public void setPostId(int postId) {
        this.id = postId;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public int getPostAuthorID() {
        return postAuthorID;
    }

    public void setPostAuthorID(int postAuthorID) {
        this.postAuthorID = postAuthorID;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean isReported) {
        this.reported = isReported;
    }
}
