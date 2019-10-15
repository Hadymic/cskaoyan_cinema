package com.cskaoyan.cinema.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
@ConfigurationProperties(prefix = "jedis")
public class JedisConfig {
    private String host = "localhost";
    private int port = 6379;

    @Bean
    public Jedis jedis() {
        return new Jedis(host, port);
    }
}
