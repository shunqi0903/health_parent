package com.shunqi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回结果
 */
@Data
public class Result implements Serializable {
    private boolean flag;
    private String message;
    private Object data;

    public  Result(boolean flag,String message){
        super();
        this.flag =flag;
        this.message = message;
    }
    public  Result(boolean flag,String message,Object data){
        this.flag =flag;
        this.message = message;
        this.data = data;
    }
}
