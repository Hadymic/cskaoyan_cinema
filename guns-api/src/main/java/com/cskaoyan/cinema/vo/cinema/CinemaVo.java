package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaVo implements Serializable {
    private static final long serialVersionUID = -7912924984186708341L;
    private Integer uuid;
<<<<<<< HEAD:guns-api/src/main/java/com/cskaoyan/cinema/vo/cinema/CinemaVo.java
   private String cinemaName;
  private  String cinemaPhone;
private String address;
private String minimumPrice;
=======
    private String cinemaName;
    private String address;
    private String minimumPrice;
>>>>>>> 82aecced46fd54d4b0c35bdbbdc7e7d982ede52d:guns-api/src/main/java/com/cskaoyan/cinema/vo/CinemaVo.java


}
