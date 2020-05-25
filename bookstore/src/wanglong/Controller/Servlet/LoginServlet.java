package wanglong.Controller.Servlet;

import wanglong.Exception.UserException;
import wanglong.Service.Impl.UserServiceImpl;
import wanglong.Service.UserService;
import wanglong.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(urlPatterns = {"/login","/loginOut"})
public class LoginServlet extends BaseServlet {
    private UserService userService=new UserServiceImpl();

    /*
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());

        /*
        通过反射调用方法

        Class cla=this.getClass();
        try {
             //通过class获取方法
            Method method = cla.getMethod(uri,HttpServletRequest.class,HttpServletResponse.class);
            //调用方法
            method.invoke(this, request,response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("未知错误");
        }
/*
        //用户退出功能
        if(uri.equals("loginOut")){
            loginOut(request,response);
        }
        //注册功能
        if(uri.equals("login")) {
            login(request,response);
        }

 /

    }
*/

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.loginUser(username, password);
            //登录成功将User存储到Session并且跳转到首页
            request.getSession().setAttribute("user_session", user);
            // request.getRequestDispatcher("/index.jsp").forward(request,response);//表单会重复提交
            if (user.getRole().equals("管理员")) {
                response.sendRedirect(request.getContextPath() + "/admin/login/home.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }


        } catch (UserException | IOException e) {
            e.printStackTrace();
            request.setAttribute("login_msg", e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    /*
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
*/

    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //初始化session(删除Session域中的user_session)
        request.getSession().invalidate();
        //退出到主页面
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }





}
