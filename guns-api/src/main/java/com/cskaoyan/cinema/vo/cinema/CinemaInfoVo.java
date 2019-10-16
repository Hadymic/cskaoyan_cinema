package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;
@Data
public class CinemaInfoVo implements Serializable {
    private static final long serialVersionUID = -5182715215972872043L;
  Integer cinemaId;
  String cinemaAdress;
  String cinemaPhone;
  String imgUrl;
  String cinemaName;

}
