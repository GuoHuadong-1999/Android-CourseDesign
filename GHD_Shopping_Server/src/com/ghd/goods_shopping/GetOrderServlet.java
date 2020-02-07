package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.Goods;
import com.ghd.goods_shopping.javaBeans.Order;
import com.ghd.goods_shopping.tools.CartDao;
import com.ghd.goods_shopping.tools.GoodsDao;
import com.ghd.goods_shopping.tools.OrderDao;
import com.ghd.goods_shopping.tools.XmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetOrderServlet")
public class GetOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String user_id = request.getParameter("user_id");
        List<Goods> goodsList = null;
        try {
            goodsList = OrderDao.selectGoodsList(Integer.parseInt(user_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String xml = XmlUtil.convertGoodsListXml(goodsList);
        System.out.println("----------->"+xml);
        response.getWriter().write(xml);
    }
}
