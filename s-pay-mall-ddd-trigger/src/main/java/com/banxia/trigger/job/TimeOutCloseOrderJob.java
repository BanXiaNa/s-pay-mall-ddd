package com.banxia.trigger.job;

import com.banxia.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author BanXia
 * @description: 定时任务
 * @Date 2025/8/19 20:25
 */
@Slf4j
@Component
public class TimeOutCloseOrderJob {

    @Resource
    private IOrderService iOrderService;

    @Scheduled(cron = "* 0/10 * * * ?")
    public void exec() {
        try {
            log.info("超时30分钟订单关闭定时任务开始执行");
            List<String> orderIds = iOrderService.queryTimeOutOrderList();
            if (null == orderIds || orderIds.isEmpty()) {
                log.info("定时任务，超时30分钟订单关闭，暂无超时未支付订单 orderIds is null");
                return;
            }
            for (String orderId : orderIds) {
                boolean status = iOrderService.changeOrderPayClose(orderId);
                log.info("超过30分钟定时关闭订单，orderId:{}, status:{}", orderId, status);
            }
        } catch (Exception e) {
            log.error("定时任务，超时15分钟订单关闭失败", e);
        }
    }

}
