package com.shunqi.dao;

import com.github.pagehelper.Page;
import com.shunqi.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SetmealDao {

     List<Map<String, Object>> hotSetmeal() ;

    public void add(Setmeal setmeal);

    public void setSetmealIdAndCheckGroupId(Map<String,Integer> map);

    public List<Setmeal> findPage(String value);

    public List<Setmeal> findAll();

    public Setmeal findById(int id);

    public Integer[] getCheckGroupIdsBySetmealId(Integer id);

    public List<Map<String,Object>> findSetmealCount();
}
