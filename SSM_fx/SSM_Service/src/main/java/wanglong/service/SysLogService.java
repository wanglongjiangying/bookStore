package wanglong.service;

import wanglong.domain.Product;
import wanglong.domain.SysLog;

import java.util.List;

public interface SysLogService {
    List<SysLog> findAll(Integer page, Integer size)throws Exception;

    void save(SysLog sysLog);
}
