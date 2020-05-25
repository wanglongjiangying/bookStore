package wanglong.Dao;

import org.apache.ibatis.annotations.*;
import wanglong.domain.Member;
import wanglong.domain.Order;
import wanglong.domain.Product;

import java.util.List;

public interface OrderDao {

    @Select("select * from orders")
    @Results({
            @Result( id = true,property = "id",column = "id"),
                    @Result(property = "orderNum",column = "orderNum"),
                    @Result(property = "orderTime",column = "orderTime"),
                    @Result(property = "orderStatus",column = "orderStatus"),
                    @Result(property = "peopleCount",column = "peopleCount"),
                    @Result(property = "payType",column = "payType"),
                    @Result(property = "orderDesc",column = "orderDesc"),

                    @Result(property = "product",column = "productId",javaType = Product.class,
                            one=@One(select = "wanglong.Dao.ProductDao.findById")),
            })
        List<Order> findAll();


    @Select("select * from orders where id=#{id}")
    @Results({
                    @Result( id = true,property = "id",column = "id"),
                    @Result(property = "orderNum",column = "orderNum"),
                    @Result(property = "orderTime",column = "orderTime"),
                    @Result(property = "orderStatus",column = "orderStatus"),
                    @Result(property = "peopleCount",column = "peopleCount"),
                    @Result(property = "payType",column = "payType"),
                    @Result(property = "orderDesc",column = "orderDesc"),

                    @Result(property = "product",column = "productId",javaType = Product.class,
                            one=@One(select = "wanglong.Dao.ProductDao.findById")),
                    @Result(property = "member",column = "memberId",javaType = Member.class,
                            one=@One(select = "wanglong.Dao.MemberDao.findById")),
                    @Result(property = "travellers",column = "id",javaType = java.util.List.class,
                            many=@Many(select="wanglong.Dao.TravellerDao.findById"))
            })
    Order findById(String id);

    @Delete("delete from orders where id=#{id}")
    void deleteById(String id)throws Exception;

    @Delete("delete from order_traveller where orderid=#{orderId}")
    void deleteOrder_traveller(String orderId)throws Exception;
}
