package com.shunqi.controller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shunqi.constant.MessageConstant;
import com.shunqi.entity.Result;
import com.shunqi.pojo.OrderSetting;
import com.shunqi.service.OrderSettingService;
import com.shunqi.util.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> fileList = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            for (String[] files : fileList) {
                Date orderDate = new Date(files[0]);
                int number = Integer.parseInt(files[1]);
                OrderSetting orderSetting = new OrderSetting(orderDate,number);
                orderSettingList.add(orderSetting);
            }
            orderSettingService.add(orderSettingList);
            return new Result(true,MessageConstant.UPLOAD_SUCCESS);
        } catch (IOException e) {
           e.printStackTrace();
           return new Result(false, "上传失败");
        }
    }
    @PostMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<OrderSetting> orderSettingList = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingList);
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            Date orderDate = orderSetting.getOrderDate();
            int number = orderSetting.getNumber();
            orderSettingService.editNumberByDate(orderDate,number);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
