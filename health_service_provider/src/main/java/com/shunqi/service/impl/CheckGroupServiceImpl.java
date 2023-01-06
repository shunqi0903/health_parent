package com.shunqi.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shunqi.dao.CheckGroupDao;
import com.shunqi.entity.PageResult;
import com.shunqi.pojo.CheckGroup;
import com.shunqi.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer id = checkGroup.getId();
        for (int i = 0; i < checkitemIds.length; i++) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("checkitem_id", checkitemIds[i]);
            map.put("checkgroup_id", id);
            checkGroupDao.setCheckItemAndCheckGourpId(map);
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<CheckGroup> list = checkGroupDao.findPage(queryString);
        PageInfo<CheckGroup> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<CheckGroup> findAll(){
        return checkGroupDao.findAll();
    }
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] ids) {
        Integer checkGroupId = checkGroup.getId();
        List<Integer> checkitemids = checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
        if(checkitemids.size() > 0){
            checkGroupDao.delCheckItemAndCheckGroupIdById(checkGroupId);
        }
        for (Integer checkitemid : ids) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("checkgroup_id", checkGroupId);
            map.put("checkitem_id", checkitemid);
            checkGroupDao.setCheckItemAndCheckGourpId(map);
        }
        checkGroupDao.edit(checkGroup);
    }
}
