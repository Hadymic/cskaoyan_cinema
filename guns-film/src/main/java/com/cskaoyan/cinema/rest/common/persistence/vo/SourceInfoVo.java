package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SourceInfoVo implements Serializable {
    private static final long serialVersionUID = 5417383548009398130L;

    private int sourceId;
    private String sourceName;
    private boolean active;
}
