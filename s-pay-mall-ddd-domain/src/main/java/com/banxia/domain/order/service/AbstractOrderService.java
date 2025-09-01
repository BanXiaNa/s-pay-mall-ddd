package com.banxia.domain.order.service;

import com.alipay.api.AlipayApiException;
import com.banxia.domain.order.adapter.port.IOrderPort;
import com.banxia.domain.order.adapter.port.IProductPort;
import com.banxia.domain.order.adapter.repository.IOrderRepository;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.model.entity.OrderEntity;
import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ProductEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;
import com.banxia.domain.order.model.valobj.OrderStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:14
 */
@Slf4j
public abstract class AbstractOrderService implements IOrderService{

    protected final IOrderRepository iOrderRepository;
    protected final IProductPort iProductPort;
    protected final IOrderPort iOrderPort;

    public AbstractOrderService(IOrderRepository iOrderRepository,
                                IProductPort iProductPort,
                                IOrderPort iOrderPort) {
        this.iOrderRepository = iOrderRepository;
        this.iProductPort = iProductPort;
        this.iOrderPort = iOrderPort;
    }

    @Override
    public PayOrderEntity createOrder(ShopCartEntity shopCartEntity) throws Exception {

        OrderEntity unpaidOrderEntity = iOrderRepository.queryOrder(shopCartEntity);

        if(null != unpaidOrderEntity && OrderStatusVO.PAY_WAIT.equals(unpaidOrderEntity.getOrderStatusVO())){
            log.info("创建订单-存在，存在未支付的订单，userId:{} productId:{} orderId:{}",shopCartEntity.getUserId(), unpaidOrderEntity.getProductId(), unpaidOrderEntity.getOrderId());
            return PayOrderEntity.builder()
                    .orderId(unpaidOrderEntity.getOrderId())
                    .payUrl(unpaidOrderEntity.getPayUrl())
                    .build();
        } else if (null != unpaidOrderEntity && OrderStatusVO.CREATE.equals(unpaidOrderEntity.getOrderStatusVO())) {
            log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} productId:{} orderId:{}", shopCartEntity.getUserId(), unpaidOrderEntity.getProductId(), unpaidOrderEntity.getOrderId());
            // 掉单订单
            return doPrepayOrder(
                    unpaidOrderEntity.getProductId(),
                    unpaidOrderEntity.getProductName(),
                    unpaidOrderEntity.getOrderId(),
                    unpaidOrderEntity.getTotalAmount()
            );
        }

        ProductEntity productEntity = iProductPort.queryProductByProductId(shopCartEntity.getProductId());

        OrderEntity orderEntity = CreateOrderAggregate.buildOrderEntity(productEntity.getProductId(), productEntity.getProductName());

        CreateOrderAggregate orderAggregate = CreateOrderAggregate.builder()
                .userId(shopCartEntity.getUserId())
                .productEntity(productEntity)
                .orderEntity(orderEntity)
                .build();


        this.saveOrder(orderAggregate);

        PayOrderEntity payOrderEntity =  doPrepayOrder(
                productEntity.getProductId(),
                productEntity.getProductName(),
                orderEntity.getOrderId(),
                productEntity.getProductPrice()
        );

        log.info("创建订单-完成，生成支付单。userId: {} orderId: {} payUrl: {}", shopCartEntity.getUserId(), orderEntity.getOrderId(), payOrderEntity.getPayUrl());

        return PayOrderEntity.builder()
                .orderId(orderEntity.getOrderId())
                .payUrl(payOrderEntity.getPayUrl())
                .build();
    }


    protected abstract void saveOrder(CreateOrderAggregate orderAggregate);

    protected abstract PayOrderEntity doPrepayOrder(String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException;
}
