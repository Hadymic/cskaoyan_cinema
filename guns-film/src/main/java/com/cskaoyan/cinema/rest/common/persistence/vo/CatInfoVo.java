package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfoVo implements Serializable {
    private static final long serialVersionUID = -1864186617500754396L;

    private int catId;
    private int catName;
    private boolean active;
}
