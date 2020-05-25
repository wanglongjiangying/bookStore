package wanglong.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanglong.Dao.PermissionDao;
import wanglong.domain.Permission;
import wanglong.service.PermissionService;

import java.util.List;

@Service("permissionService")
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findPermissionsByRoleId(String id) throws Exception {
        return permissionDao.findPermissionsByRoleId(id);
    }

    /**
     * 角色添加权限，查询出角色所没有的权限给角色添加
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<Permission> findOthersPermissions(String id) throws Exception {
        return permissionDao.findOthersPermissions(id);
    }

    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }

    @Override
    public void deleteById(String id) throws Exception {
        permissionDao.deleteFromRole_Permission(id);
        permissionDao.findById(id);
    }
}
