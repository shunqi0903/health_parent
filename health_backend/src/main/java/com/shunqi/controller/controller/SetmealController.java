package com.shunqi.controller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.constant.MessageConstant;
import com.shunqi.constant.RedisConstant;
import com.shunqi.entity.PageResult;
import com.shunqi.entity.QueryPageBean;
import com.shunqi.entity.Result;
import com.shunqi.pojo.OrderSetting;
import com.shunqi.pojo.Setmeal;
import com.shunqi.service.OrderSettingService;
import com.shunqi.service.SetmealService;
import com.shunqi.util.QiniuUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private SetmealService setmealService;


    @RequestMapping("/upload")
    public Result upload(@RequestPart("imgFile") MultipartFile imgFile){
        try {
            String originalFilename = imgFile.getOriginalFilename();
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf - 1);
            String fileName = UUID.randomUUID() + suffix;
            String path = "D:\\MyCode\\2022\\11\\health_parent\\health_backend\\src\\main\\webapp\\images\\" + originalFilename;
            QiniuUtils.upload2Qiniu(path,fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_NAME_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (Exception e) {
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_NAME_DB_RESOURCES,setmeal.getImg());
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        try {
            Integer currentPage = queryPageBean.getCurrentPage();
            Integer pageSize = queryPageBean.getPageSize();
            String queryString = queryPageBean.getQueryString();
            PageResult result = setmealService.findPage(currentPage, pageSize, queryString);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,result);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);        }

    }


}
