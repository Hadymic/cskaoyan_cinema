package com.cskaoyan.cinema.rest.util;

import com.cskaoyan.cinema.rest.config.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@Component
public class JedisUtils {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private Jedis jedis;

    public Integer getUserId(HttpServletRequest request) {
        final String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String authToken = requestHeader.substring(7);
            return Integer.parseInt(jedis.get(authToken));
        }
        return null;
    }
}
