package com.banxia.domain.order.service.impl;

import com.banxia.domain.order.adapter.port.IProductPort;
import com.banxia.domain.order.adapter.repository.IOrderRepository;
import com.banxia.domain.order.model.aggregate.CreateOrderAggregate;
import com.banxia.domain.order.service.AbstractOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 21:30
 */
@Service
@Slf4j
public class OrderService extends AbstractOrderService {



    public OrderService(IOrderRepository iOrderRepository, IProductPort iProductPort) {
        super(iOrderRepository, iProductPort);
    }

    @Override
    protected void saveOrder(CreateOrderAggregate orderAggregate) {
        iOrderRepository.doSaveOrder(orderAggregate);
    }
}
