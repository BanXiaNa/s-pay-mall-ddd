package com.banxia.domain.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.banxia.domain.order.adapter.port.IOrderPort;
import com.banxia.domain.order.adapter.port.IProductPort;
import com.banxia.domain.order.adapter.repository.IOrderRepository;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.service.AbstractOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 21:30
 */
@Service
@Slf4j
public class OrderService extends AbstractOrderService {

    public OrderService(IOrderRepository iOrderRepository, IProductPort iProductPort, IOrderPort iOrderPort) {
        super(iOrderRepository, iProductPort , iOrderPort);
    }

    @Override
    protected void saveOrder(CreateOrderAggregate orderAggregate) {
        iOrderRepository.doSaveOrder(orderAggregate);
    }

    @Override
    protected PayOrderEntity doPrepayOrder(String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException {

        PayOrderEntity payOrderEntity = iOrderPort.doPrepayOrder(productId, productName, orderId, totalAmount);

        iOrderRepository.updatePayOrderInfo(payOrderEntity);

        return payOrderEntity;

    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        iOrderRepository.changeOrderPaySuccess(orderId);
    }

    @Override
    public List<String> queryNoPayNotifyOrderList() {
        return iOrderRepository.queryNoPayNotifyOrderList();
    }

    @Override
    public List<String> queryTimeOutOrderList() {
        return iOrderRepository.queryTimeOutOrderList();
    }

    @Override
    public boolean changeOrderPayClose(String orderId) {
        return iOrderRepository.changeOrderPayClose(orderId);
    }
}
