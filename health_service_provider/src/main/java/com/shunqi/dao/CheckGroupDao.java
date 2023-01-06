package com.shunqi.dao;

import com.shunqi.pojo.CheckGroup;
import com.shunqi.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);

    public void setCheckItemAndCheckGourpId(Map<String,Integer> map);

    public List<CheckGroup> findPage(String value);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup);

    public void delCheckItemAndCheckGroupIdById(Integer id);

    public List<CheckGroup> findAll();

    public CheckGroup getCheckGroupBySetmealId(Integer id);
}
