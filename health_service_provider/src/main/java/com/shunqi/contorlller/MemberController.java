package com.shunqi.contorlller;

import com.shunqi.constant.MessageConstant;
import com.shunqi.entity.QueryPageBean;
import com.shunqi.entity.Result;
import com.shunqi.pojo.Member;
import com.shunqi.service.MemberService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/member")
@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/find",produces = {"application/json"})
    @ResponseBody
    public Result find(){
        Member member = memberService.findByTelephone("13412345678");
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,member);
    }

    @RequestMapping(value = "/findAll",produces = {"application/json"})
    public Result findAll(@Param("size") int size,@Param("page") int page){
        List<Member> memberList = memberService.findAll(size, page);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,memberList);
    }
    @PostMapping(value = "/findAll2")
    public Result findAll2(@RequestBody QueryPageBean queryPageBean){
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        List<Member> memberList = memberService.findAll(currentPage, pageSize);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,memberList);
    }
}


