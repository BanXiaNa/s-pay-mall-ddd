package com.banxia.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author BanXia
 * @description: 订单实体类
 * @Date 2025/8/15 20:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrder {
    private Integer id;
    private String userId;
    private String productId;
    private String productName;
    private String orderId;
    private Date orderTime;
    private BigDecimal totalAmount;
    private String status;
    private String payUrl;
    private Date payTime;
    private Date createTime;
    private Date updateTime;

}
