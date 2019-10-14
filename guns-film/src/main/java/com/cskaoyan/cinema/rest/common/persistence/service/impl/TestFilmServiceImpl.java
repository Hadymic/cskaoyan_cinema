package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.service.TestFilmService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = TestFilmService.class)
public class TestFilmServiceImpl implements TestFilmService {
    @Override
    public String testStr(String str) {
        return "film: " + str;
    }
}
