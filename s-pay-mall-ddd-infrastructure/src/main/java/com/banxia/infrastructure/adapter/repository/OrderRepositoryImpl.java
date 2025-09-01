package com.banxia.infrastructure.adapter.repository;

import com.alibaba.fastjson.JSON;
import com.banxia.domain.order.adapter.repository.IOrderRepository;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.model.entity.OrderEntity;
import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ProductEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;
import com.banxia.domain.order.model.valobj.OrderStatusVO;
import com.banxia.infrastructure.adapter.event.PaySuccessMessageEvent;
import com.banxia.infrastructure.dao.OrderDao;
import com.banxia.infrastructure.dao.po.PayOrder;
import com.banxia.types.event.BaseEvent;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 21:42
 */
@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    @Resource
    private OrderDao orderDao;
    @Resource
    private PaySuccessMessageEvent paySuccessMessageEvent;
    @Autowired
    private EventBus eventBus;

    @Override
    public OrderEntity queryOrder(ShopCartEntity shopCartEntity) {

        PayOrder payOrderReq = PayOrder.builder()
                .userId(shopCartEntity.getUserId())
                .productId(shopCartEntity.getProductId())
                .build();

        PayOrder unPayOrder = orderDao.queryUnPayOrder(payOrderReq);
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

        orderDao.insert(PayOrder.builder()
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

        orderDao.updatePayOrderInfo(payOrder);
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrder payOrderReq = PayOrder.builder()
                .orderId(orderId)
                .status(OrderStatusVO.PAY_SUCCESS.getCode())
                .build();
        orderDao.changeOrderPaySuccess(payOrderReq);

        // 发布支付成功事件
        BaseEvent.EventMessage<PaySuccessMessageEvent.PaySuccessMessage> eventMessage = paySuccessMessageEvent.buildEventMessage(
                PaySuccessMessageEvent.PaySuccessMessage.builder()
                        .tradeNo(orderId)
                        .build()
        );
        PaySuccessMessageEvent.PaySuccessMessage paySuccessMessage = eventMessage.getData();

        eventBus.post(paySuccessMessage);
    }

    @Override
    public List<String> queryNoPayNotifyOrderList() {
        return orderDao.queryNoPayNotifyOrderList();
    }

    @Override
    public List<String> queryTimeOutOrderList() {
        return orderDao.queryTimeOutOrderList();
    }

    @Override
    public boolean changeOrderPayClose(String orderId) {
        return orderDao.changeOrderPayClose(orderId);
    }
}
