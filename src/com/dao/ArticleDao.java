package com.dao;

import com.bean.Article;
import com.service.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleDao {

    public void addArticle(Article article) throws SQLException {
        Connection connection = Main.getConnection();
        String sql = "insert into article value (?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ? , ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, article.getId() + "");
        statement.setString(2, article.getTitle());
        statement.setString(3, article.getTime());
        statement.setString(4, article.getImgCover());
        statement.setString(5, article.getBrief());
        statement.setString(6, article.getContent());
        statement.setString(7, article.getAuthor());
        statement.setString(8, article.getType() + "");
        statement.setString(9, article.getReadNum() + "");
        statement.setString(10, article.getCommentNum() + "");
        statement.setString(11, article.getLikeNum() + "");

        statement.execute();
    }
}
