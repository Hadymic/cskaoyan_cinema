package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.cinema.CinemaService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
}
