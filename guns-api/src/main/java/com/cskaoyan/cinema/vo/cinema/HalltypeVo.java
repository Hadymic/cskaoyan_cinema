package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class HalltypeVo implements Serializable {
    private static final long serialVersionUID = 2902752812824523491L;
    private Integer halltypeId;
   private String halltypeName;
   private boolean isActive;

}
