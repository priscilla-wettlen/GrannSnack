package com.grannsnack.GrannSnack.Model;

public class ForumDTO {

    private Post post;

    private MyUser user;

    private boolean isUsers = false;

    public ForumDTO(Post post, MyUser user) {
        this.post = post;
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public MyUser getMyUser() {
        return user;
    }

    public void setMyUser(MyUser user) {
        this.user = user;
    }

    public boolean isUsers() {
        return isUsers;
    }

    public void setIsUsers(boolean users) {
        isUsers = users;
    }
}
