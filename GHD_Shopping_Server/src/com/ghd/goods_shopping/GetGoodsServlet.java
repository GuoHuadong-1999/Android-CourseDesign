package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.Goods;
import com.ghd.goods_shopping.tools.GoodsDao;
import com.ghd.goods_shopping.tools.XmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetGoodsServlet")
public class GetGoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        List<Goods> goodsList = null;
        try {
            goodsList = GoodsDao.selectGoodsList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String xml = XmlUtil.convertGoodsListXml(goodsList);
        response.getWriter().write(xml);
    }
}
