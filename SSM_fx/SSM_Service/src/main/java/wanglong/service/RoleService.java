package wanglong.service;

import wanglong.domain.Order;
import wanglong.domain.Role;

import java.util.List;

public interface RoleService  {
    List<Role> findAll(Integer page, Integer size) throws Exception;

    void save(Role role) throws Exception;

    Role findById(String id) throws Exception;

    List<Role> findRolesByUserId(String id)throws Exception;

    List<Role> findOtherRole(String id)throws Exception;

    void addPermissionToRole(String roleId, String[] permissionIds)throws Exception;
}
