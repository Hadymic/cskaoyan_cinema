package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.PromoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("promo")
public class PromoController {
    @Reference(interfaceClass = PromoService.class)
    private PromoService promoService;
}
