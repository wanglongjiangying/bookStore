package wanglong.Controller.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = "/*")
public class MyEncodingFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        //tmocat8.0自动解决get中文乱码问题，只需要解决post方法
        HttpServletRequest request=(HttpServletRequest)req;
        if(request.getMethod().equalsIgnoreCase("post")){
            req.setCharacterEncoding("UTF-8");
        }

        //解决响应数据乱码问题
        HttpServletResponse response=(HttpServletResponse)resp;
        response.setHeader("content-type","text/html;charset=utf-8");


        //放行
        chain.doFilter(req,resp);


    }

    @Override
    public void init(FilterConfig config) throws ServletException {


    }

}
