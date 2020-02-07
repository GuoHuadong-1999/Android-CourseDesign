package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.Order;
import com.ghd.goods_shopping.tools.OrderDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InsertOrderServlet")
public class InsertOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String user_id = request.getParameter("user_id");
        String[] goods_ids = request.getParameter("goods_ids").split(",");
        for (int i=0;i<goods_ids.length;i++){
            System.out.println("----------------------->goods_id:"+goods_ids[i]);
        }
        List<Integer> goodsList = new ArrayList<>();
        for (int i=0;i<goods_ids.length;i++){
            goodsList.add(Integer.parseInt(goods_ids[i]));
        }
        try {
            OrderDao.insertOrder(Integer.parseInt(user_id),goodsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
