package com.branch.demo.spring;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        final var cacheManager = new CaffeineCacheManager("GithubApi");

        // todo: inject these values if you want to use this for real...
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .recordStats()
        );

        cacheManager.setAsyncCacheMode(true);

        return cacheManager;

    }

}
