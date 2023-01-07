package com.shunqi.dao;

import com.shunqi.pojo.User;

public interface UserDao {
    public User findByUserName(String username);
}
