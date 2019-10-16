package com.cskaoyan.cinema.vo;


import java.io.Serializable;
import java.util.List;

public class ConditionVo implements Serializable {
    private static final long serialVersionUID = 2178879126845592766L;
    private List brandList;
    private List areaList;
    private List halltypeList;

    public List getBrandList() {
        return brandList;
    }

    public ConditionVo setBrandList(List brandList) {
        this.brandList = brandList;
        return this;
    }

    public List getAreaList() {
        return areaList;
    }

    public ConditionVo setAreaList(List areaList) {
        this.areaList = areaList;
        return this;
    }

    public List getHalltypeList() {
        return halltypeList;
    }

    public ConditionVo setHalltypeList(List halltypeList) {
        this.halltypeList = halltypeList;
        return this;
    }
}
