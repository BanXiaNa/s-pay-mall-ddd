package com.banxia.domain.order.adapter.port;

import com.alipay.api.AlipayApiException;
import com.banxia.domain.order.model.entity.PayOrderEntity;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 23:03
 */
public interface IOrderPort {

    PayOrderEntity doPrepayOrder(String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException;
}
