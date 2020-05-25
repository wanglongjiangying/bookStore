package wanglong.Controller.Servlet;

import org.apache.commons.beanutils.BeanUtils;
import wanglong.Service.Impl.OrderServiceImpl;
import wanglong.Service.OrderService;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;
import wanglong.domain.Product;
import wanglong.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet(urlPatterns = {"/addorder","/findOrderByUserId","/findOrderItems"})
public class OrderServlet extends HttpServlet {

    private OrderService orderService=new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String uri=requestURI.substring(requestURI.lastIndexOf("/")+1,requestURI.length());
        User user= (User)request.getSession().getAttribute("user_session");

        //判断用户是否登录
        if(user==null){
            response.getWriter().write("你还没有登录请先登录");
            return ;
        }


        if(uri.equals("addorder")){
            Map<Product,Integer> cart= (Map<Product, Integer>) request.getSession().getAttribute("cart");

            //购物车为空就不需要其他的操作
            if(cart==null||cart.size()==0){
                response.getWriter().write("购物车没有数据，不能结算，请先添加商品");
                return ;
            }


            Order order=new Order();
            try {
                //封装Order数据
                BeanUtils.populate(order,request.getParameterMap());
                order.setUser(user);
                order.setId(UUID.randomUUID().toString());
                order.setPaystate(0);
                order.setOrdertime(new Date());
                System.out.println(order);

                //封装订单详情数据（orderItem)
                List<OrderItem> orderItems=new ArrayList<>();
                Set<Product> products = cart.keySet();

                /*
                System.out.println("开始打印订单");
                OrderItem orderItem;
                for (Product product:products){
                    orderItem=new OrderItem();
                    System.out.println(product+":::"+cart.get(product));
                    orderItem.setBuynum(cart.get(product));
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    orderItems.add(orderItem);
                }*/
              //在订单方法中封装了，订单详情方便查询和返回数据
              order.setOrderItems(orderItems);
                //保存订单和订单详情   (order里面封装了orderItem)
                orderService.saveOrderAndOrderItem(order);

                //保存成功后去除cart中的数据
                request.getSession().removeAttribute("cart");

                response.sendRedirect(request.getContextPath()+"/createOrderSuccess.jsp");
            } catch (Exception e) {

                e.printStackTrace();

            }


        }

        if(uri.equals("findOrderByUserId")){

            String userId = request.getParameter("userId");

          List<Order> orderList= orderService.findOrderByUserId(Integer.parseInt(userId));
          request.setAttribute("orderAcount",orderList.size());

          request.setAttribute("orderList",orderList);

          request.getRequestDispatcher("/orderlist.jsp").forward(request,response);


        }

        if(uri.equals("findOrderItems")){

            String orderId = request.getParameter("orderId");

           Order order=orderService.findOrderAndOrderitemByOrderId(orderId);

           request.setAttribute("order",order);

           request.getRequestDispatcher("/orderInfo.jsp").forward(request,response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
