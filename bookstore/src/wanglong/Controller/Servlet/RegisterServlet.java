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
import java.util.Date;
import java.util.UUID;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserService userService=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码是否正确
           //获取表单提交的验证码
        String checkCode = request.getParameter("checkCode");


        //获取后台生成的验证码
        String checkcode_session = (String)request.getSession().getAttribute("checkcode_session");
        //比较两个验证码是否相同
       if(checkCode.equals(checkcode_session)){
           //验证码正确
           //1.获取提交的数据，封装成对象
           User user=new User();
           //1.1用BeanUtils去处理表当数据并封装到bean对象中
           try {
               BeanUtils.populate(user,request.getParameterMap());
               user.setActiveCode(UUID.randomUUID().toString());
               user.setRole("普通用户");
               user.setRegistTime(new Date());
               System.out.println(user);
               //2.注册
               userService.saveUser(user);
               request.getRequestDispatcher("/registersuccess.jsp").forward(request,response);

           } catch (UserException e){
               e.printStackTrace();
               //3.2失败
               request.setAttribute("register_error",e.getMessage());
               request.getRequestDispatcher("/register.jsp").forward(request,response);

           } catch (Exception e) {
               e.printStackTrace();
               System.out.println("参数转模型是吧");
           }

       }else{
           request.setAttribute("checkcode_error","验证码错误");
           request.getRequestDispatcher("/register.jsp").forward(request,response);
       }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doPost(request,response);
    }
}
