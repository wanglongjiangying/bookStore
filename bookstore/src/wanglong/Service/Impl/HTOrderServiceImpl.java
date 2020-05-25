package wanglong.Service.Impl;

import com.sun.mail.imap.protocol.ID;
import wanglong.Dao.HTOrderDao;
import wanglong.Dao.Impl.HTOrderDaoImpl;
import wanglong.Utils.ManagerThreadLocal;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;

import java.sql.SQLException;
import java.util.List;

public class HTOrderServiceImpl implements wanglong.Service.HTOrderService {

    private HTOrderDao htOrderDao=new HTOrderDaoImpl();

    @Override
    public List<Order> findOrders(String id, String receiverName) throws SQLException {
        return htOrderDao.findOrders(id,receiverName);
    }

    @Override
    public Order viewOrderItem(String id)throws SQLException {
        return htOrderDao.viewOrderItem(id);
    }

    /*
    删除订单同时删除订单详情
     */
    @Override
    public void deleteOrderById(String id) {
        try{
            ManagerThreadLocal.beginTransaction();

            htOrderDao.deleteOrderItemById(id);
            htOrderDao.deleteOrderById(id);

            ManagerThreadLocal.commitTransaction();

        }catch (Exception e){
            e.printStackTrace();
            ManagerThreadLocal.rollback();
        }finally {
            ManagerThreadLocal.closeConnection();
        }



    }
}
