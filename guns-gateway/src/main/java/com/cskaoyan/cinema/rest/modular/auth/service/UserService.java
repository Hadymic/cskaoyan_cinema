package com.cskaoyan.cinema.rest.modular.auth.service;

import com.cskaoyan.cinema.rest.modular.auth.controller.dto.AuthRequest;

public interface UserService {
    Integer login(AuthRequest authRequest);
}
