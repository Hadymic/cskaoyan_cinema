package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConditionNoVO implements Serializable {
    private static final long serialVersionUID = 1036584192778723090L;

    private int catId;
    private int sourceId;
    private int yearId;
}
