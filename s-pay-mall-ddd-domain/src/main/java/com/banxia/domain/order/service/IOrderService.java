package com.banxia.domain.order.service;

import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;

import java.util.List;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:12
 */
public interface IOrderService {

    PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception;

    void changeOrderPaySuccess(String orderId);

    List<String> queryNoPayNotifyOrderList();

    List<String> queryTimeOutOrderList();

    boolean changeOrderPayClose(String orderId);
}
