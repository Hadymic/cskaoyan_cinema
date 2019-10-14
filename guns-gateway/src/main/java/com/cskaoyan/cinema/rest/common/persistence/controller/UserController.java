package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.UserService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @PostMapping("register")
    public BaseRespVo register(@RequestBody UserRegisterVo vo) {
        return new BaseRespVo(1, null, null);
    }
}
