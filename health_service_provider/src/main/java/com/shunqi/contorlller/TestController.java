package com.shunqi.contorlller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("t")
@RestController
public class TestController {
    @RequestMapping("t")
    public String t(){
        return "succ";
    }
}
