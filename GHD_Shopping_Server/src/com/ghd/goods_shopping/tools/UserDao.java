package com.ghd.goods_shopping.tools;

import com.ghd.goods_shopping.javaBeans.Goods;
import com.ghd.goods_shopping.javaBeans.User;

import java.sql.*;

public class UserDao {
    private static Connection connection = null;

    static{
        try {
            connection = JDBCUtil.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static User selectUser(String name,String password) throws SQLException {
        User user = null;
        String sql = "select * from user where name = '"+name+"' and password = '"+password+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String userName = resultSet.getString(2);
            String userPassword = resultSet.getString(3);
            String image = resultSet.getString(4);
            String email = resultSet.getString(5);
            user = new User();
            user.setId(id);
            user.setName(userName);
            user.setPassword(userPassword);
            user.setImage(image);
            user.setEmail(email);
        }
        return user;
    }
    public static int insertUser(User user) throws Exception {
        Connection connection = JDBCUtil.getConn();
        String name = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();
        String sql = "insert into user values(null,?,?,null,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,email);
        return preparedStatement.executeUpdate();
    }
}
