package com.grannsnack.GrannSnack.Model;

public class ForumDTO {

    private Post post;

    private String username;

    public ForumDTO(Post post, String userName) {
        this.post = post;
        this.username = userName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
