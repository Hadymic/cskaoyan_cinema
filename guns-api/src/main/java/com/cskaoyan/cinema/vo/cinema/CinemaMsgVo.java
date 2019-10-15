package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class CinemaMsgVo implements Serializable {
    private static final long serialVersionUID = -217934579375867079L;
private   List data;
private   String imgPre;
private   String msg;
private   String nowPage;
private   String totalPage;
private   Integer status;

}
