package com.banxia.config;

import com.banxia.trigger.listener.OrderPaySuccessListener;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaConfig {

    /**
     * 微信公众号access_token缓存
     * @return Cache
     */
    @Bean(name = "weixinAccessToken")
    public Cache<String, String> weixinAccessToken(){
        // 缓存2小时
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    /**
     * 用户openid缓存
     * @return Cache
     */
    @Bean(name = "openidToken")
    public Cache<String, String> openidToken(){
        // 缓存2小时
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    @Bean
    public EventBus eventBusListener(OrderPaySuccessListener listener ) {
        EventBus eventBus = new EventBus();
        // 注册监听器
        eventBus.register(listener);
        return eventBus;
    }
}
