package com.shunqi.service;

import com.github.pagehelper.Page;
import com.shunqi.entity.PageResult;
import com.shunqi.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    public void add(Setmeal setmeal,Integer[] checkGroupIds);

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);
}
