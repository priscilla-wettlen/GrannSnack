package com.grannsnack.GrannSnack.Model;

import java.util.List;

/**
 * This class is what is called a data transfer object (DTO in short). Since sending information between backend and frontend can be complicated, sometimes one can
 * user a DTO to collect all the relevant information in one object and then sending it. Doing so requires less of the frontend and more of the backend.
 * <p>
 * This object is used to gather information for the forum page specifically. It gathers the post, the user who wrote it and the comments. This way
 * the backend can send a list of ForumDTO and send all the relevant information the frontend needs in one object.
 */
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
