package com.dao;

import com.bean.Comment;
import com.service.Main;

import java.sql.*;
import java.util.Iterator;

public class CommentDao {

    public void addComment(Comment com) throws SQLException {
        Connection con = Main.getConnection();
        String sql = "insert into comment value(?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, com.getId() + "");
        preparedStatement.setString(2, com.getArticleId() + "");
        preparedStatement.setString(3, com.getComment());
        preparedStatement.setString(4, com.getTime());
        preparedStatement.setString(5, com.getFloor() + "");
        preparedStatement.setString(6, com.getLikeNum() + "");
        preparedStatement.setString(7, com.getParentId() + "");
        preparedStatement.setString(8, com.getUserAuthor());
        preparedStatement.setString(9, com.getImgAuthor());
        preparedStatement.setString(10, com.getNameAuthor());
        preparedStatement.execute();

    }

    public int initId() throws SQLException {
        Connection connection = Main.getConnection();
        String sql = "select id from comment order by id desc limit 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
