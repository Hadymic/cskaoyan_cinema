package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class HalltypeVo implements Serializable {
    private static final long serialVersionUID = 2902752812824523491L;
    private Integer hallType;
   private String halltypeName;
   private boolean isActive;



    public String getHalltypeName() {
        return halltypeName;
    }

    public HalltypeVo setHalltypeName(String halltypeName) {
        this.halltypeName = halltypeName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public HalltypeVo setActive(boolean active) {
        isActive = active;
        return this;
    }
}
