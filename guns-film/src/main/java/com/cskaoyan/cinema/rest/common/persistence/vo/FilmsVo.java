package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmsVo<T> implements Serializable {
    private static final long serialVersionUID = -8319991555619593140L;

    private int status;
    private String imgPre;
    private int nowPage;
    private long totalPage;
    private T data;
    private String msg;
}
