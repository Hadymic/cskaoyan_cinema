package com.cskaoyan.cinema.vo.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 4526497581746211250L;
    private Integer uuid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Integer sex;
    private String birthday;
    private Integer lifeState;
    private String biography;
    private String address;
}
