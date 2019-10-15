package com.cskaoyan.cinema.vo;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaVo implements Serializable {
    private static final long serialVersionUID = -7912924984186708341L;
    private Integer uuid;
private String cinemaName;
private String address;
private String minimumPrice;


}
