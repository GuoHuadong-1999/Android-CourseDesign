package com.ghd.goods_shopping.tools;

import com.ghd.goods_shopping.javaBeans.Cart;
import com.ghd.goods_shopping.javaBeans.Goods;
import com.ghd.goods_shopping.javaBeans.User;

import java.util.List;

public class XmlUtil {
    public static String convertGoodsListXml(List<Goods> goodsList) {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xml.append("<goodsList>");
        for (Goods goods : goodsList) {
            int id = goods.getId();
            xml.append("<goods id='" + id + "'>");
            String name = goods.getName();
            String description = goods.getDescription();
            double price = goods.getPrice();
            String image = goods.getImage();
            xml.append("<name>" + name + "</name>");
            xml.append("<description>" + description + "</description>");
            xml.append("<price>" + price + "</price>");
            xml.append("<image>" + image + "</image>");
            xml.append("</goods>");
        }
        xml.append("</goodsList>");
        return xml.toString();
    }

    public static String convertGoodsXml(Goods goods) {
        StringBuffer xml = new StringBuffer();
        int id = goods.getId();
        String name = goods.getName();
        String description = goods.getDescription();
        double price = goods.getPrice();
        String image = goods.getImage();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xml.append("<goods id='" + id + "'>");
        xml.append("<name>" + name + "</name>");
        xml.append("<description>" + description + "</description>");
        xml.append("<price>" + price + "</price>");
        xml.append("<image>" + image + "</image>");
        xml.append("</goods>");
        return xml.toString();
    }
    public static String convertUserXml(User user){
        StringBuffer xml = new StringBuffer();
        int id = user.getId();
        String name = user.getName();
        String password = user.getPassword();
        String image = user.getImage();
        String email = user.getEmail();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xml.append("<user id='" + id + "'>");
        xml.append("<name>" + name + "</name>");
        xml.append("<password>" + password + "</password>");
        xml.append("<image>" + image + "</image>");
        xml.append("<email>" + email + "</email>");
        xml.append("</user>");
        return xml.toString();
    }

    public static String convertCartListXml(List<Cart> cartList) {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xml.append("<cartList>");
        for (Cart cart : cartList) {
            int id = cart.getId();
            xml.append("<cart id='" + id + "'>");
            int user_id = cart.getUser_id();
            int goods_id = cart.getGoods_id();
            xml.append("<user_id>" + user_id + "</user_id>");
            xml.append("<goods_id>" + goods_id + "</goods_id>");
            xml.append("</cart>");
        }
        xml.append("</cartList>");
        return xml.toString();
    }
}
