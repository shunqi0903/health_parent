package com.shunqi.dao;

import com.shunqi.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberDao {

    void add(Member member);

    public Member findByTelephone(@Param("telephone") String telephone);

    public int memberCount(String value);

    int thisMonthNewMember(String firstDay4ThisMonth);

    int thisWeekNewMember(String thisWeekMonday);

    int todayNewMember(String today);

    int totalMember();

    List<Member> findAll();
}
