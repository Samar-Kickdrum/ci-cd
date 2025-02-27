package com.kdu.caching.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    private static final int INITIAL_CAPACITY = 10;
    private static final int MAXIMUM_SIZE = 50;
    private static final int EXPIRE_MINUTES = 60;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(INITIAL_CAPACITY)
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .recordStats()
                .removalListener(removalListener());
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(Arrays.asList("geocoding", "reverse-geocoding"));
        return cacheManager;
    }

    @Bean
    public RemovalListener<Object, Object> removalListener() {
        return (key, value, cause) -> {
            if (cause.equals(RemovalCause.SIZE)) {
                logger.warn("Cache entry evicted due to size: {}", key);
            } else if (cause.equals(RemovalCause.EXPIRED)) {
                logger.info("Cache entry expired: {}", key);
            }
        };
    }
}