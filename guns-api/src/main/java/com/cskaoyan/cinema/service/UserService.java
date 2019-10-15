package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.user.UserRegisterVo;

public interface UserService {
    Integer register(UserRegisterVo vo);

    Integer check(String username);
}
