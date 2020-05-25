package wanglong.Dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import wanglong.domain.Order;
import wanglong.domain.Role;

import java.util.List;

public interface RoleDao {

    @Select("select * from role where id in(select roleid from users_role where userid=#{userId})")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,
              many = @Many(select = "wanglong.Dao.PermissionDao.findPermissionsByRoleId"))
    })
    List<Role> findRolesByUserId(String userId);

    @Select("select * from role")
    List<Role> findAll();

    @Insert("insert into role (roleName,roleDesc) values(#{roleName},#{roleDesc})")
    void save(Role role)throws Exception;

    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "roleName",column = "roleName"),
            @Result(property = "roleDesc",column = "roleDesc"),
            @Result(property = "permissions",column = "id",javaType = java.util.List.class,
                    many = @Many(select = "wanglong.Dao.PermissionDao.findPermissionsByRoleId"))
    })
    Role findById(String id)throws Exception;

    /**
     * 查询用户没有的角色，供用户添加角色
     * @param id
     * @return
     */
    @Select("select * from role where id not in(select roleId from users_role where userId=#{id})")
    List<Role> findOtherRole(String id)throws Exception;

    @Insert("insert into role_permission(roleId,permissionId) values(#{roleId},#{permissionId})")
    void addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId)throws Exception;
}
