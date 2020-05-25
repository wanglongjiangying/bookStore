package wanglong.service;

import wanglong.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findPermissionsByRoleId(String id)throws Exception;

    List<Permission> findOthersPermissions(String id)throws Exception;

    List<Permission> findAll()throws Exception;

    void save(Permission permission)throws Exception;

    void deleteById(String id)throws Exception;
}
