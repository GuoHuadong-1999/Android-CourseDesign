package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.Cart;
import com.ghd.goods_shopping.tools.CartDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;

@WebServlet(name = "InsertCartServlet")
public class InsertCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String user_id = request.getParameter("user_id");
        String goods_id = request.getParameter("goods_id");
        Cart cart = new Cart(Integer.parseInt(user_id),Integer.parseInt(goods_id));
        System.out.println("---------------------------->cart:"+cart);
        int number = 0;
        try {
            number = CartDao.insertCart(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------->number:"+number);
        response.getWriter().write(number);
    }
}
