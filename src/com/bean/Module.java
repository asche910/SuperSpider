package com.bean;

public class Module {
    private int id;
    private String name;
    private String brief;   //模块介绍
    private String imgCover;
    private int userNum = 0;
    private int articleNum = 0;
    private String owner ;

    public Module(int id, String name, String brief, String imgCover, int userNum, int articleNum, String owner) {
        this.id = id;
        this.name = name;
        this.brief = brief;
        this.imgCover = imgCover;
        this.userNum = userNum;
        this.articleNum = articleNum;
        this.owner = owner;
    }

    public Module() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getImgCover() {
        return imgCover;
    }

    public void setImgCover(String imgCover) {
        this.imgCover = imgCover;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
