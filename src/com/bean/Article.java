package com.bean;

public class Article {

    long id;
    String title;
    String time;
    String imgCover;
    String brief;
    String content;
    String author;
    //    文章所属模块
    int type = 0;       // 0
    int readNum = 0;    // 0
    int commentNum = 0; // 0
    int likeNum = 0;    // 0

    public Article(long id, String title, String time, String imgCover, String brief, String content, String author, int type, int readNum, int commentNum, int likeNum) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.imgCover = imgCover;
        this.brief = brief;
        this.content = content;
        this.author = author;
        this.type = type;
        this.readNum = readNum;
        this.commentNum = commentNum;
        this.likeNum = likeNum;
    }

    public Article() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }
}
