package com.grannsnack.GrannSnack.Model;

import java.util.Date;

public class Post {
    private int postId;
    private Date postDate;
    private MyUser postAuthor;
    private String postContent;
    private String postImage;

    public Post(int postId, Date postDate, MyUser postAuthor, String postContent) {
        this.postId = postId;
        this.postDate = postDate;
        this.postAuthor = postAuthor;
        this.postContent = postContent;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public MyUser getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(MyUser postAuthor) {
        this.postAuthor = postAuthor;
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
}
