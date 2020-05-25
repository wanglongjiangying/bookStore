package wanglong.Controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wanglong.domain.Permission;
import wanglong.domain.Role;
import wanglong.domain.UserInfo;
import wanglong.service.PermissionService;
import wanglong.service.RoleService;
import wanglong.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;



    @RequestMapping("/findAll.do")
    @RolesAllowed({"ADMIN","HOME"})
    public ModelAndView findAll(@RequestParam(name = "page",defaultValue ="1")Integer page,
                                @RequestParam(name = "size",defaultValue = "5")Integer size){
        try {
            ModelAndView modelAndView=new ModelAndView();
            List<UserInfo> userInfoList= userService.findAll(page,size);
            System.out.println(userInfoList);
            PageInfo pageInfo=new PageInfo(userInfoList);
            modelAndView.addObject("pageInfo",pageInfo);
            modelAndView.setViewName("user-list");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/save.do")
    @RolesAllowed("son")//只有具有son角色的用户才可以进行操作
    public String  save(UserInfo userInfo){
        try {
            userService.save(userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView  findById(@RequestParam(name = "id")String id){

        ModelAndView modelAndView=new ModelAndView();
        try {
         UserInfo userInfo=   userService.findById(id);

            modelAndView.setViewName("user-show");

            modelAndView.addObject("user",userInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        return modelAndView;
    }


    /**
     * 查询用户没有的角色，供用户添加角色
     * @param id
     * @return
     */
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id")String id){
        try {
            ModelAndView modelAndView=new ModelAndView();
           UserInfo userInfo=new UserInfo();
           userInfo.setId(id);
            modelAndView.addObject("user",userInfo);


            List<Role> roles= roleService.findOtherRole(id);

            modelAndView.addObject("roleList",roles);

            modelAndView.setViewName("user-role-add");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @RequestMapping("/addRoleToUser.do")
    @RolesAllowed("son") //只有具有son角色的用户才可以进行操作
    public String  addRoleToUser(@RequestParam(name="userId")String userId,@RequestParam(name="ids")String[] roleIds){
        try {
//            System.out.println("1:"+userId);
//            for (String id:roleIds){
//                System.out.println(id);
//            }
         userService.addRoleToUser(userId,roleIds);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:findAll.do";
    }




/*
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id")String id){
        try {
            ModelAndView modelAndView=new ModelAndView();
            Role role= new Role();
            role.setId(id);

            modelAndView.addObject("role",role);




            List<Permission> permissions= permissionService.findPermissionsByRoleId(id);

            modelAndView.addObject("permissionList",permissions);

            modelAndView.setViewName("role-permission-add");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

 */
}
