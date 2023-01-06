package com.shunqi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shunqi.service.Tservice;

@Service
public class TserviceImpl implements Tservice {
    @Override
    public String say(String name) {
        return "pr0  " + name;
    }
}
