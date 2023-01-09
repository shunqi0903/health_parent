package com.shunqi.service;

import com.github.pagehelper.Page;
import com.shunqi.entity.PageResult;
import com.shunqi.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    public void add(Setmeal setmeal,Integer[] checkGroupIds);

    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public  List<Map<String,Object>> findSetmealCount();

    List<Map<String, Object>> hotSetmeal();
}
