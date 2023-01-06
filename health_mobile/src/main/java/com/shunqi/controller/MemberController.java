package com.shunqi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.shunqi.constant.MessageConstant;
import com.shunqi.constant.RedisMessageConstant;
import com.shunqi.entity.Result;
import com.shunqi.pojo.Member;
import com.shunqi.service.MemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private MemberService memberService;
    @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletResponse response) {
//        1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String redisValidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (!validateCode.equals(redisValidateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
//        2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
        Member member = memberService.findByTelephone(telephone);
        if(member == null){
            Member member1 = new Member();
            member1.setPhoneNumber(telephone);
            member1.setRegTime(new Date());
            memberService.add(member1);
        }
//        3、向客户端写入Cookie，内容为用户手机号
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60); //30分钟
        response.addCookie(cookie);
//        4、将会员信息保存到Redis，使用手机号作为key，保存时长为30分钟
        String memberJson = JSON.toJSONString(member);
        jedisPool.getResource().setex(telephone,30*60,memberJson);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
