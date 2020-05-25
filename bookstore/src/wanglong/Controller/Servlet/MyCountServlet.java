package wanglong.Controller.Servlet;

import wanglong.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/myAcount")
public class MyCountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       User user= (User)request.getSession().getAttribute("user_session");

       if(user==null){ //没有登录跳转到登录页面
           response.sendRedirect(request.getContextPath()+"/login.jsp");
       }else{//已经登录跳转修改账户页面
           response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
       }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request,response);
    }
}
