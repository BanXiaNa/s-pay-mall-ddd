
package com.banxia.test.domain;

import com.banxia.domain.order.model.entity.PayOrderEntity;
import com.banxia.domain.order.model.entity.ShopCartEntity;
import com.banxia.domain.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private IOrderService orderService;

    @Test
    public void test_createOrder() throws Exception {
        ShopCartEntity shopCartEntity = ShopCartEntity.builder()
                .userId("banxia")
                .productId("100010090091")
                .build();

        PayOrderEntity payOrderEntity = orderService.createOrder(shopCartEntity);
        log.info("测试结果：{}", payOrderEntity);
    }

}
