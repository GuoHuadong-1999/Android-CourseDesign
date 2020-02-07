package com.ghd.goods_shopping.tools;

import com.ghd.goods_shopping.javaBeans.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static Connection connection = null;

    static {
        try {
            connection = JDBCUtil.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertOrder(int user_id, List<Integer> goodsList) throws Exception {
        Connection connection = JDBCUtil.getConn();
        for (int i = 0; i < goodsList.size(); i++) {
            int goods_id = goodsList.get(i);
            String sql = "insert into `order` values(null,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, goods_id);
            preparedStatement.executeUpdate();
        }
    }

    public static List<Goods> selectGoodsList(int user_id) throws SQLException {
        List<Goods> goodsList = new ArrayList<>();
        String sql = "select * from goods where id = any (select goods_id from `order` where user_id = "+user_id+")";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String image = resultSet.getString(5);
            Goods goods = new Goods();
            goods.setId(id);
            goods.setName(name);
            goods.setPrice(price);
            goods.setDescription(description);
            goods.setImage(image);
            goodsList.add(goods);
        }
        return goodsList;
    }
}
