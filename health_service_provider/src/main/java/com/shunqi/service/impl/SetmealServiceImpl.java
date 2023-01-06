package com.shunqi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shunqi.constant.RedisConstant;
import com.shunqi.dao.CheckGroupDao;
import com.shunqi.dao.CheckItemDao;
import com.shunqi.dao.SetmealDao;
import com.shunqi.entity.PageResult;
import com.shunqi.pojo.CheckGroup;
import com.shunqi.pojo.CheckItem;
import com.shunqi.pojo.Setmeal;
import com.shunqi.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String outputPath;

    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add(setmeal);
        int id = setmeal.getId();
        for (Integer checkGroupId : checkGroupIds) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkGroupId);
            setmealDao.setSetmealIdAndCheckGroupId(map);
        }
        // 完成数据操作后需要将图片的名称保持到redis，用于将七牛云中的图片与redis中图片存在差异时删除多余图片使用；
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_NAME_DB_RESOURCES,setmeal.getImg());
        // 新增套餐后需要生成静态页面
        generateMobileStaticHtml();
    }

    private void generateMobileStaticHtml() {
        // 准备生成套餐模板的套餐数据集合
        List<Setmeal> setmealList = this.findAll();
        // 生成套餐列表模板
        generateMobileSetmealListHtml(setmealList);
        // 生成耽搁套餐模板
        generateMobileSetmealDetailHtml(setmealList);
    }

    private void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("setmeal",setmeal);
            generateHtml("mobile_setmeal_detail.ftl","mobile_setmeal_"+setmeal.getId()+".html",dataMap);
        }
    }

    private void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("setmealList",setmealList);

        this.generateHtml("mobile_setmeal.ftl","mobile_setmeal.html",dataMap);

    }
    public void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap){
        //1.创建配置类
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        //2.设置模板所在的目录
        //3.设置字符集
        //以上两步在配置文件中已经指定
        //4.加载模板
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File docFile = new File(outputPath+"\\"+htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(docFile.toPath())));
            // 输出文件
            template.process(dataMap,out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Setmeal> list = setmealDao.findPage(queryString);
        PageInfo<Setmeal> info = new PageInfo<>(list);
        long total = info.getTotal();
        List<Setmeal> result = info.getList();
        return new PageResult(total, result);
    }

    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> setmealList = setmealDao.findAll();
        return setmealList;
    }

    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public Setmeal findById(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        Integer[] checkGroupIds = setmealDao.getCheckGroupIdsBySetmealId(id);
        List<CheckGroup> checkGroupList = new ArrayList<>();
        for (Integer checkGroupId : checkGroupIds) {
            CheckGroup checkGroup = checkGroupDao.getCheckGroupBySetmealId(checkGroupId);
            List<Integer> checkItemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
            List<CheckItem> checkItemList = new ArrayList<>();
            for (Integer checkItemId : checkItemIds) {
                CheckItem checkItem = checkItemDao.findById(checkItemId);
                checkItemList.add(checkItem);
            }
            checkGroup.setCheckItems(checkItemList);
            checkGroupList.add(checkGroup);
        }
        setmeal.setCheckGroups(checkGroupList);
        return setmeal;
    }

}
