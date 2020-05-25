package wanglong.Dao;

import org.apache.ibatis.annotations.Select;
import wanglong.domain.Traveller;

import java.util.List;

public interface TravellerDao {

    @Select("select * from traveller where id in(select travellerid from order_traveller where orderid=#{id})")
     List<Traveller> findById(String id)throws Exception;
}
