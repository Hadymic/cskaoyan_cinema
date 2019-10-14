package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.service.TestUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = TestUserService.class)
public class TestUserServiceImpl implements TestUserService {
    @Override
    public String testStr(String str) {
        return "user: " + str;
    }
}
