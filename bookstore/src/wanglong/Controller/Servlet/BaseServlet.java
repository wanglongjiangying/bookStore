package wanglong.Controller.Servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());
        /*
        通过反射调用方法
         */
        Class cla=this.getClass();
        try {
            //通过class获取方法
            Method method = cla.getMethod(uri, HttpServletRequest.class, HttpServletResponse.class);
            //调用方法
            method.invoke(this, request,response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("未知错误");
        }
    }


}
