package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.service.PayService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = PayService.class)
public class PayServiceImpl implements PayService {
}
