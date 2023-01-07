package com.shunqi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shunqi.dao.PermissionDao;
import com.shunqi.dao.RoleDao;
import com.shunqi.dao.UserDao;
import com.shunqi.pojo.Permission;
import com.shunqi.pojo.Role;
import com.shunqi.pojo.User;
import com.shunqi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;
    @Override
    public User findByUserName(String username) {
        User user = userDao.findByUserName(username);
        Set<Role> roleSet = roleDao.findByUserId(user.getId());
        user.setRoles(roleSet);
        for (Role role : roleSet) {
            Set<Permission> permissionSet = permissionDao.findByRoleId(role.getId());
            role.setPermissions(permissionSet);
        }
        return user;
    }
}
