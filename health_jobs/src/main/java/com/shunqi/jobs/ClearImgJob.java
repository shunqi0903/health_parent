package com.shunqi.jobs;

import cn.hutool.db.nosql.redis.RedisDS;
import com.shunqi.constant.RedisConstant;
import com.shunqi.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        Set<String> sdiffSet = 
                jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_NAME_RESOURCES, RedisConstant.SETMEAL_PIC_NAME_DB_RESOURCES);
        for (String picName : sdiffSet) {
            QiniuUtils.deleteFileFromQiniu(picName);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_NAME_RESOURCES,picName);
            System.out.println("zxl");
        }
    }
}
