package wanglong.Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import wanglong.domain.Permission;

import java.util.List;

public interface PermissionDao {

    @Select("select * from permission where id in(select permissionId from role_permission where roleId=#{roleId})")
   List<Permission> findPermissionsByRoleId(String roleId)throws Exception;

    /**
     * 角色添加权限，查询出角色所没有的权限给角色添加
     * @param id
     * @return
     */
    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    List<Permission> findOthersPermissions(String id)throws Exception;


    @Select("select * from permission")
    List<Permission> findAll()throws Exception;

    @Insert("insert into permission(permissionName,url)values(#{permissionName},#{url})")
    void save(Permission permission)throws Exception;

    @Delete("delete from permission where id=#{id}")
    void findById(String id)throws Exception;
    @Delete("delete from role_permission where permissionId=#{id}")
    void deleteFromRole_Permission(String id);
}
