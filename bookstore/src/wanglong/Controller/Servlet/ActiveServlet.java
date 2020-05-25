package wanglong.Controller.Servlet;

import wanglong.Exception.UserException;
import wanglong.Service.Impl.UserServiceImpl;
import wanglong.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/active"})
public class ActiveServlet extends HttpServlet {
    private UserService userService=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String activeCode = request.getParameter("activeCode");
       // response.setCharacterEncoding("UTF-8");
        System.out.println(activeCode);

        response.setHeader("content-type","text/html;charset=utf-8");
        try {
            userService.activeUser(activeCode);
            response.getWriter().write("激活成功");
        } catch (UserException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doPost(request,response);
    }
}
