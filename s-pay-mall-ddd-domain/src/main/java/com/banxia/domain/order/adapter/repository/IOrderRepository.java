package com.banxia.domain.order.adapter.repository;

import com.alipay.api.AlipayApiException;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.model.entity.OrderEntity;
import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:20
 */
public interface IOrderRepository {

    OrderEntity queryOrder(ShopCartEntity shopCartEntity);


    void doSaveOrder(CreateOrderAggregate orderAggregate);


    void updatePayOrderInfo(PayOrderEntity payOrderEntity);
}
