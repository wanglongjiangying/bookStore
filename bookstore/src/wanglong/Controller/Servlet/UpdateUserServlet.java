package wanglong.Controller.Servlet;

import org.apache.commons.beanutils.BeanUtils;
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
import java.lang.reflect.InvocationTargetException;

@WebServlet(urlPatterns = {"/updateUser","/findUserById"})
public class UpdateUserServlet extends HttpServlet {
    private UserService userService=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());
        if(uri.equals("updateUser")){
            User user=new User();
            try {
                BeanUtils.populate(user,request.getParameterMap());
                userService.updateUser(user);
                response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");

            } catch (UserException u){
                u.printStackTrace();
                response.getWriter().write(u.getMessage());
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        if(uri.equals("findUserById")) {
            String userId = request.getParameter("userId");
            User user = null;
            try {
                user = userService.findUserById(Integer.parseInt(userId));
                request.setAttribute("user", user);
                request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
            } catch (UserException e) {
                e.printStackTrace();
                response.getWriter().write(e.getMessage());
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
    }
}
