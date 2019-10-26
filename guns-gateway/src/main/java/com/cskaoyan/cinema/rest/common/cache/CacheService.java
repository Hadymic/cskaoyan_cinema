package com.cskaoyan.cinema.rest.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author Hadymic
 */
@Component
public class CacheService {
    private Cache<String, Object> cache;

    @PostConstruct
    private void init() {
        cache = CacheBuilder.newBuilder()
                //设置cache初始容量值
                .initialCapacity(10)
                //设置最大容量值，达到最大容量大小之后，会按照LRU策略进行淘汰
                .maximumSize(100)
                //设置过期时间
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    public void setCacheInLocal(String key, Object val) {
        cache.put(key, val);
    }

    public <T> T getCacheInLocal(String key, Class<T> clazz) {
        Object val = cache.getIfPresent(key);
        if (clazz.isInstance(val)) {
            return clazz.cast(val);
        }
        return null;
    }
}
