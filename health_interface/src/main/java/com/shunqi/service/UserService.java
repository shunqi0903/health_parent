package com.shunqi.service;

import com.shunqi.pojo.User;

public interface UserService {

    public User findByUserName(String username);
}
