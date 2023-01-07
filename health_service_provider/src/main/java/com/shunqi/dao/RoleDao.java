package com.shunqi.dao;

import com.shunqi.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(Integer id);
}
