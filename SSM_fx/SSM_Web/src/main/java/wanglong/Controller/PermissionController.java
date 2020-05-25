package wanglong.Controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.domain.Permission;
import wanglong.domain.UserInfo;
import wanglong.service.PermissionService;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        try {
            ModelAndView modelAndView=new ModelAndView();
            List<Permission> permissionList= permissionService.findAll();
            modelAndView.addObject("permissionList",permissionList);
            modelAndView.setViewName("permission-list");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/save.do")
    public String save(Permission permission){
        try {

            permissionService.save(permission);

            return "redirect:findAll.do";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/deletePermission.do")
    public String deletePermission(@RequestParam(name="id") String id) throws Exception {
        permissionService.deleteById(id);
        return "redirect:findAll.do";
    }


}
