package com.banxia.domain.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    // 商品ID
    private String productId;
    // 商品名称
    private String productName;
    // 商品描述
    private String productDesc;
    // 商品价格
    private BigDecimal productPrice;
}
