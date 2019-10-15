package com.cskaoyan.cinema.vo.cinema;

import com.cskaoyan.cinema.vo.CinemaVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaRespVo implements Serializable {
    private static final long serialVersionUID = -3516924798448632438L;
    private List<CinemaVo> data;
    private String imgPre;
    private String msg;
    private Integer nowPage;
    private Integer status;
    private Integer totalPage;


}
