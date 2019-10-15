package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class YearInfoVo implements Serializable {
    private static final long serialVersionUID = -8370358416364003676L;

    private int yearId;
    private String yearName;
    private boolean active;
}
