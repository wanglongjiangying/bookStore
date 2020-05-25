package wanglong.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanglong.Dao.RoleDao;
import wanglong.domain.Order;
import wanglong.domain.Role;
import wanglong.service.RoleService;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;


    @Override
    public List<Role> findAll(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        List<Role> orders=roleDao.findAll();
        return orders;
    }

    @Override
    public void save(Role role) throws Exception{
        roleDao.save(role);
    }

    @Override
    public Role findById(String id) throws Exception {
        return roleDao.findById(id);
    }

    @Override
    public List<Role> findRolesByUserId(String id) throws Exception {
        return roleDao.findRolesByUserId(id);
    }

    /**
     * 查询用户没有的角色，供用户添加角色
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> findOtherRole(String id) throws Exception {
        return roleDao.findOtherRole(id);
    }

    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) throws Exception {
        for(String permissionId:permissionIds){
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }

}
