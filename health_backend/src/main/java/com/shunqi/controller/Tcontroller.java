package com.shunqi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.entity.QueryPageBean;
import com.shunqi.entity.Result;
import com.shunqi.service.Tservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/findAll")
    @ResponseBody
    public Result findAll(@RequestBody QueryPageBean queryPageBean){
        return new Result(true,"suc",queryPageBean);
    }
}
