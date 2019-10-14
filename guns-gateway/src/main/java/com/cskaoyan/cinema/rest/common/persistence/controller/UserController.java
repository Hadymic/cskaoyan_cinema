package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Reference(interfaceClass = UserService.class)
    private UserService userService;
}
