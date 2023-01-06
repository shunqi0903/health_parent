package com.shunqi.controller;

import com.aliyuncs.exceptions.ClientException;
import com.shunqi.constant.MessageConstant;
import com.shunqi.constant.RedisMessageConstant;
import com.shunqi.entity.Result;
import com.shunqi.util.SMSUtils;
import com.shunqi.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @GetMapping("/send4Order")
    public Result send4Order(String telephone) {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode.toString());
        } catch (ClientException e) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,
                5 * 60, String.valueOf(validateCode));
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
