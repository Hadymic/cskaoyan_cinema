package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.service.PromoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
}

