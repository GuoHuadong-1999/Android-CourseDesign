package com.ghd.goods_shopping.tools;

import com.ghd.goods_shopping.javaBeans.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private static Connection connection = null;

    static{
        try {
            connection = JDBCUtil.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int insertCart(Cart cart) throws Exception {
        Connection connection = JDBCUtil.getConn();
        int user_id = cart.getUser_id();
        int goods_id = cart.getGoods_id();
        String sql = "insert into cart values(null,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,user_id);
        preparedStatement.setInt(2,goods_id);
        return preparedStatement.executeUpdate();
    }
    public static List<Cart> selectCartList(int user_id) throws SQLException {
        List<Cart> cartList = new ArrayList<>();
        String sql = "select * from cart where user_id = " + user_id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            int goods_id = resultSet.getInt(3);
            Cart cart = new Cart(id,user_id,goods_id);
            cartList.add(cart);
        }
        return cartList;
    }

    public static void deleteGoods(int user_id, List<Integer> goodsList) throws Exception {
        Connection connection = JDBCUtil.getConn();
        for (int i = 0; i < goodsList.size(); i++) {
            int goods_id = goodsList.get(i);
            System.out.println("user_id:"+user_id+"\tgoods_id:"+goods_id);
            String sql = "DELETE FROM cart WHERE user_id = ? and goods_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, goods_id);
            preparedStatement.executeUpdate();
        }
    }
}
