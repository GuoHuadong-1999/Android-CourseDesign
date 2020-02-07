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

@WebServlet(name = "GoodsDetailServlet")
public class GoodsDetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String goodsId = request.getParameter("id");
        Goods goods = null;
        try {
            goods = GoodsDao.selectGoods(Integer.parseInt(goodsId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String xml = XmlUtil.convertGoodsXml(goods);
        response.getWriter().write(xml);
    }
}
