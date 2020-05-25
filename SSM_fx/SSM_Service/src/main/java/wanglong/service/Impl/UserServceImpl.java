package wanglong.service.Impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import wanglong.Dao.UserDao;
import wanglong.domain.Role;
import wanglong.domain.UserInfo;
import wanglong.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userDao.findByName(username);

        System.out.println(userInfo);
        User user=new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==0?false:true,true,true,true, getAuthority(userInfo.getRoles()));
        return user;
    }
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list=new ArrayList<>();
        for(Role role:roles){
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

        System.out.println(list);
        return list;
    }

    @Override
    public List<UserInfo> findAll(Integer page,Integer size) throws Exception {

        PageHelper.startPage(page,size);
        List<UserInfo> userInfoList=  userDao.findAll();

        return userInfoList;
    }

    @Override
    public void save(UserInfo userInfo) {
       userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id)throws Exception {
        return userDao.findById(id);
    }


    @Override
    public void addRoleToUser( String userId,String[] roleIds) throws Exception {
        for(String roleId:roleIds){
            userDao.addRoleToUser(userId,roleId);
        }

    }


}
