package com.banxia.infrastructure.dao;


import com.banxia.infrastructure.dao.po.PayOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/15 20:13
 */
@Mapper
public interface OrderDao {

    void insert (PayOrder payOrder);

    PayOrder queryUnPayOrder(PayOrder payOrderReq);

    void updatePayOrderInfo(PayOrder payOrder);

    void changeOrderPaySuccess(PayOrder order);

    boolean changeOrderPayClose(String orderId);

    List<String> queryTimeOutOrderList();

    List<String> queryNoPayNotifyOrderList();
}
