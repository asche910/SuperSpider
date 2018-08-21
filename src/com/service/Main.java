package com.service;

import java.sql.*;

/**
 * 建站数据SuperSpider（简书）
 * 本项目目的：
 * 为练习web开发提供相关的数据；
 * 主要数据包括：
 * 简书热门专题模块信息、对应模块下的热门文章、
 * 文章的详细信息、作者信息、
 * 评论区详细信息、评论者信息等...
 * 最后存储mysql数据库.
 *
 * @author As_
 * @date 2018-08-21 12:53:42
 * @github https://github.com/apknet
 */

public class Main {

    /**
     * 下面补全数据库信息
     */
    static String URL = "jdbc:mysql://localhost:3306/myDatabase";
    static String user = "USER";
    static String password = "PASSWORD";

    private static Connection con = null;

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, user, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return con;
    }

    public static void main(String[] args) throws SQLException {

        new ModuleSpider().run();

    }
}
