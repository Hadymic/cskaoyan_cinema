package com.cskaoyan.cinema.consistant;

/**
 * redis存储，前缀分类类
 */
public class CachePrefixConsistant {
    //缓存前缀
    public static final String PROMO_CACHE_KEY_IN_REDIS_PREFIX = "promo_cache_key_prefix_cinemaid_";

    //库存缓存前缀
    public static final String PROMO_STOCK_CACHE_PREFIX = "promo_stock_cache_prefix_";

    //库存售罄缓存key前缀
    public static final String PROMO_STOCK_NULL_PREFIX = "promo_stock_null_prefix_";

    //秒杀令牌token存放缓存前缀
    public static final String PROMO_USER_TOKEN_PREFIX = "promo_user_token_prefix_";

    //秒杀令牌数量限制缓存前缀
    public static final String PROMO_STOCK_AMOUNT_LIMIT = "promo_stock_amount_limit_";
}
