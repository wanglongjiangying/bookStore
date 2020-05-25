package wanglong.Service.Impl;

import wanglong.Dao.Impl.OrderDaoImpl;
import wanglong.Dao.Impl.ProductDaoImpl;
import wanglong.Dao.OrderDao;
import wanglong.Dao.ProductDao;
import wanglong.Service.OrderService;
import wanglong.Utils.ManagerThreadLocal;
import wanglong.domain.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao=new OrderDaoImpl();

    private ProductDao productDao=new ProductDaoImpl();


    /**
     * 这个方法存在事务问题
     * @param order
     */
    @Override
    public void saveOrderAndOrderItem(Order order) {
        try {
            //开起线程管理
            ManagerThreadLocal.beginTransaction();
            //添加订单
            orderDao.addOrder(order);
            //添加订单详情
            orderDao.addOrderItems(order);
            //商品卖出去以后要改变商品的库存数量
            productDao.updatePnum(order);

            ManagerThreadLocal.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
             ManagerThreadLocal.rollback();
        }



    }

    @Override
    public List<Order>  findOrderByUserId(int userId) {
        try {

            List<Order> orders = orderDao.findOrderByUserId(userId);

            return orders;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查看订单详情
     * @param orderId
     * @return
     */

    @Override
    public Order findOrderAndOrderitemByOrderId(String orderId) {
        try {
            Order order = orderDao.findOrderAndOrderitemsByOrderId(orderId);
            return order;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
