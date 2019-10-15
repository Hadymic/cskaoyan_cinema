package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import com.cskaoyan.cinema.vo.user.UserVo;

public interface UserService {
    Integer register(UserRegisterVo vo);

    Integer check(String username);

    Integer logout(String token);

    BaseRespVo selectUserInfo(String token);

    BaseRespVo updateUserInfo(UserVo userVo);
}
