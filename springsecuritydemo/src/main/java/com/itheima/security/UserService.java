package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService implements UserDetailsService {
    public static Map<String, User> map = new HashMap<>();
    static {
        User user1 = new User("admin", "admin");
        User user2 = new User("xiaoming", "1234");
        map.put(user1.getUserName(),user1);
        map.put(user2.getUserName(),user2);
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("userName"+userName);
        User userInDb = map.get(userName);
        if(userInDb == null){
            //根据用户名没有查询到用户
            return null;
        }
        //模拟数据库中的密码，后期需要查询数据库
        String passwordInDb = "{noop}" + userInDb.getPassWord();

        List<GrantedAuthority> list = new ArrayList<>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        list.add(new SimpleGrantedAuthority("/add"));
        list.add(new SimpleGrantedAuthority("/delete"));
        if("admin".equals(userName)){
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userName, passwordInDb, list);
        return user;
    }
}
