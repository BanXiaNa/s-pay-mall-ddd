package com.banxia.domain.order.model.entity;

import com.banxia.domain.order.model.valobj.OrderStatusVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderEntity {
    private String userId;

    private String orderId;

    private String payUrl;

    private OrderStatusVO orderStatusVO;
}
