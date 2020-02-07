package com.ghd.goods_shopping;

import com.ghd.goods_shopping.javaBeans.User;
import com.ghd.goods_shopping.tools.UserDao;
import com.ghd.goods_shopping.tools.XmlUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = UserDao.selectUser(name,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user==null){
            response.getWriter().write("登录失败");
        }else{
            String xml = XmlUtil.convertUserXml(user);
            response.getWriter().write(xml);
        }
    }
}
