package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmVO<T> implements Serializable {

    private static final long serialVersionUID = -6344628449402352784L;

    private Integer status;
    private T data;
    private String imgPre;
    private String msg;

    public FilmVO(int status, T data, String imgPre) {
        this.status = status;
        this.data = data;
        if (imgPre != null) {
            this.imgPre = imgPre;
        } else {
            this.imgPre = "http://img.meetingshop.cn/";
        }
    }

    public FilmVO(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
