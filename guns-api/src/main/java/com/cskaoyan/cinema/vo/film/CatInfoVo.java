package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class CatInfoVo implements Serializable {
    private static final long serialVersionUID = -1864186617500754396L;

    private int catId;
    private String catName;
    private boolean active;
}
