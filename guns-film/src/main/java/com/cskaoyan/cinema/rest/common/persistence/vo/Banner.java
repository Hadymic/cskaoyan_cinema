package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Banner implements Serializable {
    private static final long serialVersionUID = 192013973788534093L;
    private Integer bannerId;
    private String bannerAddress;
    private String bannerUrl;

    public Banner(Integer bannerId, String bannerAddress, String bannerUrl) {
        this.bannerId = bannerId;
        this.bannerAddress = bannerAddress;
        this.bannerUrl = bannerUrl;
    }
}
