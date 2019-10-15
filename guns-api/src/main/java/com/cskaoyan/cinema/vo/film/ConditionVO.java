package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConditionVO<T> implements Serializable {

    private static final long serialVersionUID = -3098100279020199200L;
    private int status;
    private T data;
}
