package wanglong.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanglong.Dao.SysLogDao;
import wanglong.domain.Product;
import wanglong.domain.SysLog;
import wanglong.service.SysLogService;

import java.util.List;

@Service("sysLogService")
@Transactional
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public List<SysLog> findAll(Integer page, Integer size) throws Exception {

        PageHelper.startPage(page,size);
        List<SysLog> sysLogs=  sysLogDao.findALl();
        return sysLogs;
    }

    @Override
    public void save(SysLog sysLog) {

        try{
            sysLogDao.save(sysLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
