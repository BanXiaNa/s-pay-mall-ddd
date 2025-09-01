package com.banxia.domain.order.adapter.port;

import com.banxia.domain.order.model.entity.ProductEntity;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 20:22
 */
public interface IProductPort {
    ProductEntity queryProductByProductId(String productId);
}
