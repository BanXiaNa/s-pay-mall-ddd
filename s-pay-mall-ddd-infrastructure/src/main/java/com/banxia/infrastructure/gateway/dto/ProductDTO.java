package com.banxia.infrastructure.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description: 商品信息
 * @Date 2025/8/15 21:06
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    // 商品ID
    private String productId;
    // 商品名称
    private String productName;
    // 商品描述
    private String productDesc;
    // 商品价格
    private BigDecimal productPrice;
}
