package com.grannsnack.GrannSnack.Model;

import java.util.List;

public class ForumDTO {

    private Post post;
    private MyUser user;
    private List<Comment> commentList;
    private boolean isUsers = false;

    public ForumDTO(Post post, MyUser user, List<Comment> commentList) {
        this.post = post;
        this.user = user;
        this.commentList = commentList;
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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public boolean isUsers() {
        return isUsers;
    }

    public void setIsUsers(boolean users) {
        isUsers = users;
    }
}
