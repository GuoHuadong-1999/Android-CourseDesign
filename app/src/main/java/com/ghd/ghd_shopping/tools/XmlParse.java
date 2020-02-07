package com.ghd.ghd_shopping.tools;

import com.ghd.ghd_shopping.javaBean.Goods;
import com.ghd.ghd_shopping.javaBean.User;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParse {
    public static List<Goods> getGoodsList(String xml) throws XmlPullParserException, IOException {
        List<Goods> goodsList = null;
        Goods goods = null;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                // 开始解析某个结点
                case XmlPullParser.START_TAG: {
                    if ("goodsList".equals(nodeName)) {
                        goodsList = new ArrayList<>();
                    } else if ("goods".equals(nodeName)) {
                        goods = new Goods();
                        goods.setId(Integer.parseInt(parser.getAttributeValue(0)));
                    } else if("name".equals(nodeName)){
                        goods.setName(parser.nextText());
                    }else if("description".equals(nodeName)){
                        goods.setDescription(parser.nextText());
                    }else if("price".equals(nodeName)){
                        goods.setPrice(Double.parseDouble(parser.nextText()));
                    }else if("image".equals(nodeName)){
                        goods.setImage(parser.nextText());
                    }
                    break;
                }
                // 完成解析某个结点
                case XmlPullParser.END_TAG: {
                    if ("goods".equals(nodeName)) {
                        goodsList.add(goods);
                        goods = null;
                    }
                    break;
                }
                default:
                    break;
            }
            eventType = parser.next();
        }
        return goodsList;
    }
    public static Goods getGoods(String xml) throws XmlPullParserException, IOException {
        Goods goods = null;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(xml));
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                // 开始解析某个结点
                case XmlPullParser.START_TAG: {
                   if ("goods".equals(nodeName)) {
                        goods = new Goods();
                        goods.setId(Integer.parseInt(parser.getAttributeValue(0)));
                    } else if("name".equals(nodeName)){
                        goods.setName(parser.nextText());
                    }else if("description".equals(nodeName)){
                        goods.setDescription(parser.nextText());
                    }else if("price".equals(nodeName)){
                        goods.setPrice(Double.parseDouble(parser.nextText()));
                    }else if("image".equals(nodeName)){
                        goods.setImage(parser.nextText());
                    }
                    break;
                }
                // 完成解析某个结点
                case XmlPullParser.END_TAG: {
                    if ("goods".equals(nodeName)) {
                        return goods;
                    }
                    break;
                }
                default:
                    break;
            }
            eventType = parser.next();
        }
        return goods;
    }
    public static User getUser(String xml) throws XmlPullParserException, IOException {
        User user = null;
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        parser.setInput(is, "utf-8");
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String nodeName = parser.getName();
            switch (eventType) {
                // 开始解析某个结点
                case XmlPullParser.START_TAG: {
                    if ("user".equals(nodeName)) {
                        user = new User();
                        user.setId(Integer.parseInt(parser.getAttributeValue(0)));
                    } else if("name".equals(nodeName)){
                        user.setName(parser.nextText());
                    }else if("password".equals(nodeName)){
                        user.setPassword(parser.nextText());
                    }else if("image".equals(nodeName)){
                        user.setImage(parser.nextText());
                    }else if("email".equals(nodeName)){
                        user.setEmail(parser.nextText());
                    }
                    break;
                }
                default:
                    break;
            }
            eventType = parser.next();
        }
        return user;
    }
}
