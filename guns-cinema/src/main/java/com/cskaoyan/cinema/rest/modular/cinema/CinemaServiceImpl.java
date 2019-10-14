package com.cskaoyan.cinema.rest.modular.cinema;

import com.cskaoyan.cinema.cinema.CinemaService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
}
