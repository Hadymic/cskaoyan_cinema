package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.TestCinemaService;
import com.cskaoyan.cinema.service.TestFilmService;
import com.cskaoyan.cinema.service.TestUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Reference(interfaceClass = TestFilmService.class)
    private TestFilmService testFilmService;

    @Reference(interfaceClass = TestCinemaService.class)
    private TestCinemaService testCinemaService;

    @Reference(interfaceClass = TestUserService.class)
    private TestUserService testUserService;

    @RequestMapping("tf")
    public String testFilm() {
        return testFilmService.testStr("hello");
    }

    @RequestMapping("tc")
    public String testCinema() {
        return testCinemaService.testStr("hello");
    }

    @RequestMapping("tu")
    public String testUser() {
        return testUserService.testStr("hello");
    }
}
