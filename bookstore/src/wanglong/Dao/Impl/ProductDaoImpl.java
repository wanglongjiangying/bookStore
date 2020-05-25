package wanglong.Dao.Impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import wanglong.Dao.ProductDao;
import wanglong.Utils.C3p0Utils;
import wanglong.Utils.ManagerThreadLocal;
import wanglong.domain.Order;
import wanglong.domain.OrderItem;
import wanglong.domain.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Long getCount(String category) throws SQLException {
        long count=0;
        QueryRunner qr= new QueryRunner(C3p0Utils.getDataSource());
        StringBuilder sqlBuilder=new StringBuilder(" select count(0) from products where 1=1 ");
        if(category!=null&&!("".equals(category))){
            sqlBuilder.append(" and category=? ");
            //查询分类总数
            count =(long) qr.query(sqlBuilder.toString(), new ScalarHandler(), category);
        }else{
            //查询全部
            count =(long) qr.query(sqlBuilder.toString(), new ScalarHandler());
        }

        return count;
    }

    @Override
    public List<Product> findBookBypage(String category, int currentPage,int pageSize)throws SQLException {
        QueryRunner qr= new QueryRunner(C3p0Utils.getDataSource());
        //添加参数，并且转化为数组
       List<Object> list=new ArrayList<>();
       //拼接字符串
        StringBuilder sqlBuilder=new StringBuilder(" select * from products where 1=1 ");
        if(category!=null&&!("".equals(category))){
            sqlBuilder.append(" and category= ? ");
            list.add(category);
        }
        //添加分页
        sqlBuilder.append("limit ? ,?");
        int start=(currentPage-1)*pageSize;
        int end=pageSize;
        list.add(start);
        list.add(end);
        List<Product> productList = qr.query(sqlBuilder.toString(), new BeanListHandler<Product>(Product.class), list.toArray());

        return productList;
    }


    @Override
    public Product findBookById(int id) {
        try {
            QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());
            String sql = "select * from products where id =?";
            Product product = qr.query(sql, new BeanHandler<Product>(Product.class),id);
            return product;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 添加订单后改变库存数量
     * @param order

    @Override
    public void updatePnum(Order order) {
        try {
            QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());
            //新库存数量=旧库存数量-卖出的数量
            String sql = "update products set pnum=pnum-? where id = ?";

            List<OrderItem> orderItems = order.getOrderItems();
            for(OrderItem orderItem:orderItems){
                qr.update(sql,orderItem.getBuynum(),orderItem.getProduct().getId());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
     */
    /**
     * 用批处理来优化
     * @param order
     */
    @Override
    public void updatePnum(Order order) throws SQLException {

            //

            //新库存数量=旧库存数量-卖出的数量
            String sql = "update products set pnum=pnum-? where id = ?";
            //
            List<OrderItem> orderItems = order.getOrderItems();

            Object[][] params=new Object[orderItems.size()][];

            for(int i=0;i<orderItems.size();i++){
                OrderItem orderItem = orderItems.get(i);
                params[i]=new Object[]{orderItem.getBuynum(),orderItem.getProduct().getId()};
            }

      /*  QueryRunner qr = new QueryRunner(C3p0Utils.getDataSource());
            //执行批处理
            qr.batch(sql,params);*/

      //用事务管理，添加订单，订单详情，改变库存用同一个连接
          QueryRunner qr=new QueryRunner();
          qr.batch(ManagerThreadLocal.getConnection(),sql,params);


    }

}
