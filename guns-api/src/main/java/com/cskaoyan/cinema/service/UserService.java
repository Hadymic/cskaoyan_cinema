package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import com.cskaoyan.cinema.vo.user.UserVo;

public interface UserService {
    Integer register(UserRegisterVo vo);

    Integer check(String username);

    BaseRespVo selectUserInfo(Integer userId);

    BaseRespVo updateUserInfo(UserVo userVo);
}
