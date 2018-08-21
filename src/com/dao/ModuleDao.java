package com.dao;

import com.bean.Module;
import com.service.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModuleDao {

    public void addModule(Module module) throws SQLException {
        Connection connection = Main.getConnection();

        String sql = "insert into module value (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, module.getId() + "");
        preparedStatement.setString(2, module.getName());
        preparedStatement.setString(3, module.getBrief());
        preparedStatement.setString(4, module.getImgCover());
        preparedStatement.setString(5, module.getUserNum() + "");
        preparedStatement.setString(6, module.getArticleNum() + "");
        preparedStatement.setString(7, module.getOwner());

        preparedStatement.execute();
    }
}
