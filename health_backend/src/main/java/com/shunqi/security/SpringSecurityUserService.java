package com.shunqi.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.pojo.Permission;
import com.shunqi.pojo.Role;
import com.shunqi.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SpringSecurityUserService  implements UserDetailsService {
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //远程调用用户服务，根据用户名查询用户信息,用户名不存在返回null
        com.shunqi.pojo.User user = userService.findByUserName(username);
        if(user == null){
            return null;
        }
        //授角色和授权
        Set<Role> roleSet = user.getRoles();
        List<GrantedAuthority> list= new ArrayList<>();
        for (Role role : roleSet) {
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissionSet = role.getPermissions();
            for (Permission permission : permissionSet) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new User(username,user.getPassword(),list);
    }
}
