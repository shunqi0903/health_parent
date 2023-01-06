package com.shunqi.service;

import com.shunqi.entity.PageResult;
import com.shunqi.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    public void add(CheckGroup checkGroup,Integer[] checkitemIds);

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    public CheckGroup findById(Integer id);

    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    public void edit(CheckGroup checkGroup, Integer[] ids);

    public List<CheckGroup> findAll();
}
