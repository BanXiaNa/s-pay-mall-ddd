package com.banxia.infrastructure.adapter.port;

import com.banxia.domain.order.adapter.port.IProductPort;
import com.banxia.domain.order.model.entity.ProductEntity;
import com.banxia.infrastructure.dao.IOrderDao;
import com.banxia.infrastructure.gateway.ProductRPC;
import com.banxia.infrastructure.gateway.dto.ProductDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 21:38
 */
@Component
public class ProductPortImpl implements IProductPort {

    private final ProductRPC productRPC;

    public ProductPortImpl(ProductRPC productRPC) {
        this.productRPC = productRPC;
    }

    @Override
    public ProductEntity queryProductByProductId(String productId) {
        ProductDTO productDTO = productRPC.queryProductByProductId(productId);

        return ProductEntity.builder()
                .productId(productDTO.getProductId())
                .productName(productDTO.getProductName())
                .productDesc(productDTO.getProductDesc())
                .productPrice(productDTO.getProductPrice())
                .build();
    }
}
