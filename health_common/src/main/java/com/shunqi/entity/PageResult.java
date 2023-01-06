package com.shunqi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
public class PageResult implements Serializable {
    private long total;
    private List rows;

    public PageResult(long total,List rows){
        this.total = total;
        this.rows = rows;
    }
}
