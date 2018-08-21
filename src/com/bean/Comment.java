package com.bean;

public class Comment {

//    评论ID 6位长度, 依次递增
    private int id;
    private long articleId;
    private String comment;
    private String time;
//    楼层
    private int floor;
    private int likeNum = 0;    // 0
//    用来标示是否为回复的评论-->sub-comment
    private int parentId = 0;   //0
//    用户信息
    private String userAuthor;
    private String imgAuthor = null;
    private String nameAuthor;


    public Comment(int id, long articleId, String comment, String time, int floor, int likeNum, int parentId, String userAuthor, String imgAuthor, String nameAuthor) {
        this.id = id;
        this.articleId = articleId;
        this.comment = comment;
        this.time = time;
        this.floor = floor;
        this.likeNum = likeNum;
        this.parentId = parentId;
        this.userAuthor = userAuthor;
        this.imgAuthor = imgAuthor;
        this.nameAuthor = nameAuthor;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(String userAuthor) {
        this.userAuthor = userAuthor;
    }

    public String getImgAuthor() {
        return imgAuthor;
    }

    public void setImgAuthor(String imgAuthor) {
        this.imgAuthor = imgAuthor;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }
}
