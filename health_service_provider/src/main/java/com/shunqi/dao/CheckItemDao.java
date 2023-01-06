package com.shunqi.dao;

import com.github.pagehelper.Page;
import com.shunqi.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public void delete(Integer id);

    public  long findCountByCheckItemId(Integer checkitemid);

    public CheckItem findById(int id);

    public void edit(CheckItem checkItem);

    public List<CheckItem> findAll();
}
