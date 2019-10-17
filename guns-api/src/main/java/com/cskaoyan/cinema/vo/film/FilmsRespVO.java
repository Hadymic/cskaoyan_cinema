package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmsRespVO<T> implements Serializable {
    private static final long serialVersionUID = -8319991555619593140L;

    private int status;
    private String imgPre;
    private int nowPage;
    private long totalPage;
    private T data;
    private String msg;
}
