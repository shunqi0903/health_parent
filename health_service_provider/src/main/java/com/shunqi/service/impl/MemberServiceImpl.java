package com.shunqi.service.impl;

import com.shunqi.dao.MemberDao;
import com.shunqi.pojo.Member;
import com.shunqi.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> memberCount(List<String> list) {
        List<Integer> count = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String value = list.get(i) + ".31";
            int memberCount = memberDao.memberCount(value);
            count.add(memberCount);
        }
        return count;
    }
}
