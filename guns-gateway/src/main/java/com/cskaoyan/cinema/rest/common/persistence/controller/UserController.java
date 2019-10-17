package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.rest.config.properties.JwtProperties;
import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.service.UserService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.user.UserRegisterVo;
import com.cskaoyan.cinema.vo.user.UserVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference(interfaceClass = UserService.class)
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private Jedis jedis;
    @Autowired
    private JedisUtils jedisUtils;

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


    @GetMapping("logout")
    public BaseRespVo logout(HttpServletRequest request) {//Authorization
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            Long del = jedis.del(authToken);
            if (del == 1) {
                return new BaseRespVo(0, null, "成功退出");
            } else if (del == 0) {
                return new BaseRespVo(1, null, "退出失败，用户尚未登录");
            }
        }
        return new BaseRespVo(999, null, "系统出现异常，请联系管理员");
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

    @GetMapping("getUserInfo")
    public BaseRespVo getUserInfo(HttpServletRequest request) {
        Integer userId = jedisUtils.getUserId(request);
        BaseRespVo baseRespVo = userService.selectUserInfo(userId);
        return baseRespVo;
    }

    @PostMapping("updateUserInfo")
    public BaseRespVo updateUserInfo(UserVo userVo) {
        BaseRespVo baseRespVo = userService.updateUserInfo(userVo);
        return baseRespVo;
    }

}

