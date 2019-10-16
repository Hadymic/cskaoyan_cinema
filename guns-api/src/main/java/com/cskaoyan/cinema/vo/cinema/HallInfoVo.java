package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVo implements Serializable {
    private static final long serialVersionUID = 6293739177840771874L;
    Integer hallFieldId;
    String hallName;
    String price;
    String seatFile;
    String soldSeats;
}
