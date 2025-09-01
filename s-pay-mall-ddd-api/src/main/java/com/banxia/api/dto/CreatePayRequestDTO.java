package com.banxia.api.dto;

import lombok.Data;

/**
 * @Author BanXia
 * @description: 创建订单请求参数
 * @Date 2025/8/18 13:34
 */
@Data
public class CreatePayRequestDTO {

    private String userId;

    private String productId;
}
