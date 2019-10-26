package com.cskaoyan.cinema.vo.cinema;


import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaQueryVo implements Serializable {
    private static final long serialVersionUID = -5955243656014211912L;
    private Integer brandId;
    private Integer hallType;
    private Integer areaId;
    private Integer pageSize;
    private Integer nowPage;


}
