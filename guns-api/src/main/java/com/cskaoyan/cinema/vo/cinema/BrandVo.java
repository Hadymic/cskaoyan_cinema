package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVo implements Serializable {
    private static final long serialVersionUID = -3302142977346382558L;
    private Integer brandId;
    private String  brandName;
    private boolean isActive;

    public Integer getBrandId() {
        return brandId;
    }

    public BrandVo setBrandId(Integer brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getBrandName() {
        return brandName;
    }

    public BrandVo setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public BrandVo setActive(boolean active) {
        isActive = active;
        return this;
    }
}
