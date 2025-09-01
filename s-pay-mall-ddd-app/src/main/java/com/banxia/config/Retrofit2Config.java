package com.banxia.config;


import com.banxia.infrastructure.gateway.IWeixinApiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @Author BanXia
 * @description: 配置Retrofit2
 * @Date 2025/8/13 13:10
 */
@Configuration
public class Retrofit2Config {

    //  微信API的基础URL
    private static final String BASE_URL = "https://api.weixin.qq.com/";

    /**
     * 创建Retrofit实例
     * @return Retrofit实例
     */
    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * 创建IWeixinApiService实例
     * @param retrofit Retrofit实例
     * @return IWeixinApiService实例
     */
    @Bean
    public IWeixinApiService iWeixinApiService(Retrofit retrofit) {
        return retrofit.create(IWeixinApiService.class);
    }
}
