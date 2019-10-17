package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ListBean<T>  implements Serializable {
    private static final long serialVersionUID = 1274154548960270131L;
    private   List<T> data;
private   Integer nowPage;
private   long totalPage;
private  String msg;
private  String imgPre;
private  Integer status;
}
