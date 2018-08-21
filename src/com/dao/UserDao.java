package com.dao;

import com.bean.User;
import com.service.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public  void addUser(User user) throws Exception{
        Connection con = Main.getConnection();
        String sql = ""+
                "insert into user"+
                "(user, password, name, imgAvatar, signature, email, userGroup, money)"+ //此处选全会导致mysql的default无效
                "value"+
                "(?, ?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1, user.getUser());
        pre.setString(2, user.getPassword());
        pre.setString(3, user.getName());
        pre.setString(4, user.getImgAvatar());
        pre.setString(5, user.getSignature());
        pre.setString(6, user.getEmail());
        pre.setString(7, user.getUserGroup() + "");
        pre.setString(8, user.getMoney() + "");
        pre.execute();
    }

    public User get(String user)throws Exception{
        User user_2 = null;
        Connection con = Main.getConnection();
        String sql = "" +
                "select *from login" +
                " where user=?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1, user);
        ResultSet res = pre.executeQuery();
        while(res.next()){
            user_2 = new User();
            user_2.setUser(res.getString("user"));
            user_2.setPassword(res.getString("password"));
            user_2.setName(res.getString("name"));
            user_2.setImgAvatar(res.getString("imgAvatar"));
            user_2.setSignature(res.getString("signature"));
            user_2.setEmail(res.getString("email"));
            user_2.setUserGroup(Integer.parseInt(res.getString("userGroup")));
            user_2.setMoney(Integer.parseInt(res.getString("money")));
        }
        return user_2;
    }


//   以下待更新

    public void updateUser(User user) throws Exception{
        Connection con = Main.getConnection();
        String sql = ""+
                "update login "+
                "set password=?, name=?" +
                "where user=?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1, user.getPassword());
        pre.setString(2, user.getName());
        pre.setString(3, user.getUser());
        pre.execute();
    }

    public void delUser(String user) throws Exception{
        Connection con = Main.getConnection();
        String sql = "" +
                "delete from login " +
                "where user=?";
        PreparedStatement pre = con.prepareStatement(sql);
        pre.setString(1, user);
        pre.execute();
    }



    public List<User> query() throws Exception{
        Connection con = Main.getConnection();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("select user, name from login");
        List<User> userList = new ArrayList<>();
        User user = null;
        while(res.next()){
            user = new User();
            user.setUser(res.getString("user"));
            user.setName(res.getString("name"));
            userList.add(user);
        }
        return userList;
    }
}
