package com.banxia.domain.auth.adapter.port;

import java.io.IOException;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 11:43
 */
public interface ILoginPort {

    String createQrCodeTicket() throws IOException;

    void sendLoginTemplate(String openId) throws IOException;
}
