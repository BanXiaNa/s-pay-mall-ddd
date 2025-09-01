package com.banxia.trigger.listener;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author BanXia
 * @description: 订单支付成功监听器
 * @Date 2025/8/19 19:51
 */
@Slf4j
@Component
public class OrderPaySuccessListener {

    @Subscribe
    public void handleEvent(String paySuccessMessage) {
        log.info("订单支付成功：{}", paySuccessMessage);
    }

}
