package wanglong.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.expression.spel.ast.Projection;
import wanglong.domain.Product;

import java.util.List;

public interface ProductDao {
    @Select("select * from product")
    List<Product> findAll();

    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice," +
            "productDesc,productStatus) values(#{productNum},#{productName},#{cityName}," +
            "#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);

    @Select("select * from product where id=#{id}")
    Product findById(String id);

    @Delete("delete from product where id=#{id}")
    void deleteById(String id);

    @Update("update product set productNum=#{productNum},productName=#{productName},cityName=#{cityName},departureTime=#{departureTime}," +
            "productPrice=#{productPrice},productDesc=#{productDesc},productStatus=#{productStatus} where id=#{id}")
        void update(Product product);

    @Select("select * from product where id like #{search} or productNum like #{search}  or productName like #{search} or cityName like #{search} or departureTime like #{search} " +
            "or productPrice like #{search} or productDesc like #{search} or productStatus like #{search}")
    List<Product> search(String search);
}
