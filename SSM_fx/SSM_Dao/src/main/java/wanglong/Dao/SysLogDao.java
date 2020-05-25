package wanglong.Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import wanglong.domain.SysLog;

import java.util.List;

public interface SysLogDao {

    @Select("select * from Syslog")
    List<SysLog> findALl() throws Exception;

    @Insert("insert into Syslog(visitTime,username,ip,url,executionTime,method) values(" +
            " #{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    void save(SysLog sysLog)throws Exception;
}
