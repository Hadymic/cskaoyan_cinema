package com.cskaoyan.cinema.rest.modular.auth.service.impl;

import com.cskaoyan.cinema.rest.common.persistence.dao.UserTMapper;
import com.cskaoyan.cinema.rest.modular.auth.controller.dto.AuthRequest;
import com.cskaoyan.cinema.rest.modular.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTMapper userTMapper;

    @Override
    public Integer login(AuthRequest authRequest) {
        return userTMapper.selectByUsernameAndPassword(authRequest.getUserName(), authRequest.getPassword());
    }
}
