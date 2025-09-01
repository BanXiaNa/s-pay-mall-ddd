package com.banxia.infrastructure.adapter.repository;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.banxia.domain.order.adapter.repository.IOrderRepository;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.model.entity.OrderEntity;
import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ProductEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;
import com.banxia.domain.order.model.valobj.OrderStatusVO;
import com.banxia.infrastructure.dao.IOrderDao;
import com.banxia.infrastructure.dao.po.PayOrder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 21:42
 */
@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    @Resource
    private IOrderDao iOrderDao;

    @Override
    public OrderEntity queryOrder(ShopCartEntity shopCartEntity) {

        PayOrder payOrderReq = PayOrder.builder()
                .userId(shopCartEntity.getUserId())
                .productId(shopCartEntity.getProductId())
                .build();

        PayOrder unPayOrder = iOrderDao.queryUnPayOrder(payOrderReq);
        if(unPayOrder == null){
            return null;
        }

        return OrderEntity.builder()
                .productId(unPayOrder.getProductId())
                .productName(unPayOrder.getProductName())
                .orderId(unPayOrder.getOrderId())
                .orderTime(unPayOrder.getOrderTime())
                .totalAmount(unPayOrder.getTotalAmount())
                .orderStatusVO(OrderStatusVO.valueOf(unPayOrder.getStatus()))
                .payUrl(unPayOrder.getPayUrl())
                .build();
    }

    @Override
    public void doSaveOrder(CreateOrderAggregate orderAggregate) {

        OrderEntity orderEntity = orderAggregate.getOrderEntity();
        ProductEntity productEntity = orderAggregate.getProductEntity();

        iOrderDao.insert(PayOrder.builder()
                .userId(orderAggregate.getUserId())
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .orderId(orderEntity.getOrderId())
                .totalAmount(productEntity.getProductPrice())
                .orderTime(orderEntity.getOrderTime())
                .status(orderEntity.getOrderStatusVO().getCode())
                .build());

    }

    @Override
    public void updatePayOrderInfo(PayOrderEntity payOrderEntity) {
        PayOrder payOrder = PayOrder.builder()
                .userId(payOrderEntity.getUserId())
                .orderId(payOrderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .status(payOrderEntity.getOrderStatusVO().getCode())
                .build();

        iOrderDao.updatePayOrderInfo(payOrder);
    }
}
