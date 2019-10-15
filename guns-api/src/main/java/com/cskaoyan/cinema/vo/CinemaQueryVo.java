package com.cskaoyan.cinema.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaQueryVo implements Serializable {
    private static final long serialVersionUID = -5955243656014211912L;
    private Integer brandId=99;
    private Integer  hallType=99;
    private Integer  districtId=99;
    private  Integer areaId=99;
    private Integer  pageSize=12;
    private Integer  nowPage=1;


}
