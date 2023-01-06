package com.shunqi.dao;

import com.shunqi.pojo.Member;

public interface MemberDao {

    void add(Member member);

    public Member findByTelephone(String telephone);
}
