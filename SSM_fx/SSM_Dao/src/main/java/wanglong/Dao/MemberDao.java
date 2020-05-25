package wanglong.Dao;

import org.apache.ibatis.annotations.Select;
import wanglong.domain.Member;

public interface MemberDao {

    @Select("select * from member where id=#{id}")
    Member findById(String id);


}
