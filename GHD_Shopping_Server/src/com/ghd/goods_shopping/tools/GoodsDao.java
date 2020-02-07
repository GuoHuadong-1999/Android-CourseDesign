package com.ghd.goods_shopping.tools;

import com.ghd.goods_shopping.javaBeans.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao {
    private static Connection connection = null;

    static{
        try {
            connection = JDBCUtil.getConn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int insertGoods(Goods goods) throws Exception {
        Connection connection = JDBCUtil.getConn();
        String name = goods.getName();
        String description = goods.getDescription();
        double price = goods.getPrice();
        String image = goods.getImage();
        String sql = "insert into goods values(null,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,description);
        preparedStatement.setDouble(3,price);
        preparedStatement.setString(4,image);
        return preparedStatement.executeUpdate();
    }
    public static List<Goods> selectGoodsList() throws SQLException {
        List<Goods> goodsList = new ArrayList<>();
        String sql = "select * from goods";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery(sql);
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

    public static Goods selectGoods(int goodsId) throws SQLException {
        Goods goods = null;
        String sql = "select * from goods where id = "+goodsId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String image = resultSet.getString(5);
            goods = new Goods();
            goods.setId(id);
            goods.setName(name);
            goods.setPrice(price);
            goods.setDescription(description);
            goods.setImage(image);
        }
        return goods;
    }
    public static List<Goods> selectGoodsList(int user_id) throws SQLException {
        List<Goods> goodsList = new ArrayList<>();
        String sql = "select * from goods where id = any (select goods_id from cart where user_id = "+user_id+")";
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
    public static List<Goods> selectGoodsList(String goods_information) throws SQLException {
        List<Goods> goodsList = new ArrayList<>();
        String sql = "select * from goods where name like '%"+goods_information+"%' or description like '%"+goods_information+"%'";
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
