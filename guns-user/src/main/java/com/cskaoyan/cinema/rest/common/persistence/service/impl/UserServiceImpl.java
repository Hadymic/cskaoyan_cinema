package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.rest.common.persistence.dao.UserTMapper;
import com.cskaoyan.cinema.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserTMapper userTMapper;
}
