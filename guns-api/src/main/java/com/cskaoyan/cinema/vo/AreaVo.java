package com.cskaoyan.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AreaVo implements Serializable {
    private static final long serialVersionUID = -4379973935367982104L;
    private Integer areaId;
   private String areaName;
   private boolean isActive;

    public Integer getAreaId() {
        return areaId;
    }

    public AreaVo setAreaId(Integer areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getAreaName() {
        return areaName;
    }

    public AreaVo setAreaName(String areaName) {
        this.areaName = areaName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public AreaVo setActive(boolean active) {
        isActive = active;
        return this;
    }
}
