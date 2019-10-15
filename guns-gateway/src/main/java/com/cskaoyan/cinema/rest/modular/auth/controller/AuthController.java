package com.cskaoyan.cinema.rest.modular.auth.controller;

import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.rest.common.exception.BizExceptionEnum;
import com.cskaoyan.cinema.rest.modular.auth.controller.dto.AuthRequest;
import com.cskaoyan.cinema.rest.modular.auth.controller.dto.AuthResponse;
import com.cskaoyan.cinema.rest.modular.auth.service.UserService;
import com.cskaoyan.cinema.rest.modular.auth.util.JwtTokenUtil;
import com.cskaoyan.cinema.rest.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "dbValidator")
    private IReqValidator reqValidator;

    @Autowired
    private Jedis jedis;

    @Autowired
    private UserService userService;

    @PostMapping(value = "${jwt.auth-path}")
    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) {

        boolean validate = reqValidator.validate(authRequest);

        //用户登录的真正逻辑
        Integer userId = userService.login(authRequest);

        if (validate) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);

            //产生token之后和userId绑定起来，存入redis
            jedis.set(token, "" + userId);
            //redis过期时长5个小时
            jedis.expire(token, 18000);

            return ResponseEntity.ok(new AuthResponse(token, randomKey));
        } else {
            throw new GunsException(BizExceptionEnum.AUTH_REQUEST_ERROR);
        }
    }
}
