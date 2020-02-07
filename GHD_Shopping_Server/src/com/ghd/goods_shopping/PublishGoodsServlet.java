package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.Goods;
import com.ghd.goods_shopping.tools.GoodsDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PublishGoods")
public class PublishGoodsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("goods_name");
        String description = request.getParameter("goods_description");
        String price = request.getParameter("goods_price");
        String image = request.getParameter("goods_image");

        Goods goods = new Goods(name,description,Double.parseDouble(price),image);

        try {
            GoodsDao.insertGoods(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/goods/goods_manage/index.jsp");
    }
}
