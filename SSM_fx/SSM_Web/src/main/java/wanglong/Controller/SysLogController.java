package wanglong.Controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.Dao.SysLogDao;
import wanglong.domain.Product;
import wanglong.domain.SysLog;
import wanglong.service.SysLogService;

import java.util.List;

@Controller
@RequestMapping("sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;


    @RequestMapping("/findAll.do")
    @Secured({"ROLE_son","ROLE_ADMIN"})
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue ="1")Integer page,
                                @RequestParam(name="size",required = true,defaultValue = "20")Integer size){

        ModelAndView modelAndView=new ModelAndView();
        List<SysLog> sysLogs = null;
        try {
            sysLogs = sysLogService.findAll(page,size);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PageInfo pageInfo=new PageInfo(sysLogs);

        modelAndView.setViewName("sysLog-list");

        modelAndView.addObject("pageInfo",pageInfo);

        return modelAndView;
    }


}
