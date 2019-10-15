package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.UserService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    private UserService userService;

    @PostMapping("register")
    public BaseRespVo register(@Valid UserRegisterVo vo) {
        Integer checkCode = userService.check(vo.getUsername());
        if (checkCode != 0) {
            return new BaseRespVo(1, null, "用户已存在");
        }
        Integer code = userService.register(vo);
        if (code == 1) {
            return new BaseRespVo(0, null, "注册成功");
        } else {
            return new BaseRespVo(999, null, "系统出现异常，请联系管理员");
        }
    }

    @PostMapping("check")
    public BaseRespVo check(String username) {
        Integer code = userService.check(username);
        if (code == 0) {
            return new BaseRespVo(0, null, "用户名不存在");
        } else {
            return new BaseRespVo(1, null, "用户已经注册");
        }
    }

}
