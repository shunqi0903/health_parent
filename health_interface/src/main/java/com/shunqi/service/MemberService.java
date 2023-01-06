package com.shunqi.service;

import com.shunqi.pojo.Member;

public interface MemberService {
    public Member findByTelephone(String telephone);
    public void add(Member member);
}
