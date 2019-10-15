package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;
@Data
public class FilmFields implements Serializable {
    private static final long serialVersionUID = 6932377776984150840L;
private   String beginTime;
private   String endTime;
private   Integer fieldId;
private   String hallName;
private   String language;
private   String price;
}
