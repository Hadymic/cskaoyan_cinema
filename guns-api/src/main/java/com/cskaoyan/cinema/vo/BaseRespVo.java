package com.cskaoyan.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回json的基础返回类
 *
 * @param <T>
 * @author hadymic
 */
@Data
public class BaseRespVo<T> implements Serializable {
    private static final long serialVersionUID = -8364967086003877027L;

    private int status;
    private String imgPre;
    private T data;
    private String msg;

    public BaseRespVo(int status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public BaseRespVo(int status, String imgPre, T data, String msg) {
        this.status = status;
        this.imgPre = imgPre;
        this.data = data;
        this.msg = msg;
    }
}
