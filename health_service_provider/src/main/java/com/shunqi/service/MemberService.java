package com.shunqi.service;

import com.github.pagehelper.Page;
import com.shunqi.pojo.Member;

import java.util.List;

public interface MemberService {
    public Member findByTelephone(String telephone);

    public void add(Member member);

    public List<Integer> memberCount(List<String> list);

    List<Member> findAll(int size,int page);
}
