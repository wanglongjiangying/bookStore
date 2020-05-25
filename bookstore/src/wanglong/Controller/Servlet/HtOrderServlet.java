package wanglong.Controller.Servlet;

import com.mysql.cj.util.StringUtils;
import wanglong.Service.HTOrderService;
import wanglong.Service.Impl.HTOrderServiceImpl;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;
import wanglong.domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/findOrders","/admin/viewOrderItem","/admin/deleteOrderById"})
public class HtOrderServlet extends BaseServlet {

    private HTOrderService htOrderService=new HTOrderServiceImpl();

    public void deleteOrderById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        htOrderService.deleteOrderById(id);
        response.sendRedirect(request.getContextPath()+"/admin/findOrders");

    }

    public void findOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            String receiverName = request.getParameter("receiverName");
            String id = request.getParameter("id");
            List<Order> orderList = htOrderService.findOrders(id, receiverName);

            for(Order order:orderList){
                System.out.println("???"+order);
            }

            request.setAttribute("orderList",orderList);
            request.getRequestDispatcher("/admin/orders/list.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();

            response.getWriter().write("查询订单出错");
        }


    }

    public void viewOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");

            Order order = htOrderService.viewOrderItem(id);

            request.setAttribute("order", order);

            request.getRequestDispatcher("/admin/orders/view.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();

            response.getWriter().write("未知错误");
        }
    }
}


