package wanglong.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import wanglong.domain.UserInfo;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserInfo> findAll(Integer page,Integer size) throws Exception;

    void save(UserInfo userInfo);

    UserInfo findById(String id)throws Exception;

    void addRoleToUser(String userId, String[] roleIds)throws Exception;
}
