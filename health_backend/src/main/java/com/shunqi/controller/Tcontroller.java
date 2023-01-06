package com.shunqi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.service.Tservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Tcontroller {
    @Reference
    private Tservice tservice;

    @RequestMapping("/get")
    @ResponseBody
    public String get(String name){
        return tservice.say(name);
    }
}
