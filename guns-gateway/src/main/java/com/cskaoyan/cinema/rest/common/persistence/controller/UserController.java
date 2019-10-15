package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.UserService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @PostMapping("register")
    public BaseRespVo register(@RequestBody UserRegisterVo vo) {
        return new BaseRespVo();
    }

    @GetMapping("logout")
    public BaseRespVo logout() {//Authorization
        return new BaseRespVo();
    }

    @GetMapping("getUserInfo")
    public BaseRespVo getUserInfo() {
        BaseRespVo baseRespVo = userService.selectUserInfo();
        return baseRespVo;
    }

    @PostMapping("updateUserInfo")
    public BaseRespVo updateUserInfo(){

    }
}
