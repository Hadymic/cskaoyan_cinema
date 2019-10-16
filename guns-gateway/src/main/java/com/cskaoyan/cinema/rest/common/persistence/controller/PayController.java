package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.PayService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {
    @Reference(interfaceClass = PayService.class)
    private PayService payService;
}
