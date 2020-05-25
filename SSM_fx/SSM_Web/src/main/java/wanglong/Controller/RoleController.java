package wanglong.Controller;

import com.github.pagehelper.PageInfo;
import org.apache.log4j.lf5.viewer.categoryexplorer.TreeModelAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.domain.Order;
import wanglong.domain.Permission;
import wanglong.domain.Role;
import wanglong.service.PermissionService;
import wanglong.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue ="1")Integer page,
                                @RequestParam(name="size",required = true,defaultValue = "5")Integer size) throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        List<Role> roles=roleService.findAll(page,size);
        PageInfo pageInfo=new PageInfo(roles);
        modelAndView.setViewName("role-list");
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }

    @RequestMapping("/save.do")
    public String save(Role role){
        try {
            roleService.save(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:findAll.do";
    }


    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name="id")String id){
        try {
            ModelAndView modelAndView=new ModelAndView();
          Role role= roleService.findById(id);
          modelAndView.setViewName("role-show");

          modelAndView.addObject("role",role);
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name="id")String id){
        try {
            ModelAndView modelAndView=new ModelAndView();
            Role role= new Role();
            role.setId(id);
            modelAndView.addObject("role",role);

           List<Permission> permissions= permissionService.findOthersPermissions(id);
           modelAndView.addObject("permissionList",permissions);

            modelAndView.setViewName("role-permission-add");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId")String roleId,@RequestParam(name="ids")String[] permissionIds){
        try {
            roleService.addPermissionToRole(roleId,permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:findAll.do";
    }
}
