package com.shunqi.service;

import com.shunqi.entity.PageResult;
import com.shunqi.pojo.CheckItem;

import java.util.List;

/**
 * 检查项服务接口
 */
public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    public void delete(Integer id);

    public CheckItem findById(Integer id);

    public List<CheckItem> findAll();
    public void edit(CheckItem checkItem);
}
