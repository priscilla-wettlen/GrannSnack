package com.grannsnack.GrannSnack.Model;

public class Post {
    private int postId;
    private String postDate;
    private MyUser postAuthor;
    private String postContent;
    private String postImage;

    public Post(int postId, String postDate, MyUser postAuthor, String postContent) {
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

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
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
