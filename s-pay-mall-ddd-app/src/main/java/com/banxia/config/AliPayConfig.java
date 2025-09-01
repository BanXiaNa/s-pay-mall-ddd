package com.banxia.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author BanXia
 * @description: Alipay配置类
 * @Date 2025/8/16 23:03
 */
@Configuration
@EnableConfigurationProperties(AliPayConfigProperties.class)
public class AliPayConfig {

    /**
     * 用于构造AlipayClient对象 便于创建支付宝客户端
     * @param aliPayConfigProperties 支付宝配置属性
     * @return AlipayClient
     */
    @Bean(name = "alipayClient")
    public AlipayClient alipayClient(AliPayConfigProperties aliPayConfigProperties) {
        return new DefaultAlipayClient(
                aliPayConfigProperties.getGatewayUrl(),
                aliPayConfigProperties.getApp_id(),
                aliPayConfigProperties.getMerchant_private_key(),
                aliPayConfigProperties.getFormat(),
                aliPayConfigProperties.getCharset(),
                aliPayConfigProperties.getAlipay_public_key(),
                aliPayConfigProperties.getSign_type()
                );
    }
    

}
