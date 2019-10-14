package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.service.TestCinemaService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = TestCinemaService.class)
public class TestCinemaServiceImpl implements TestCinemaService {
    @Override
    public String testStr(String str) {
        return "cinema: " + str;
    }
}
