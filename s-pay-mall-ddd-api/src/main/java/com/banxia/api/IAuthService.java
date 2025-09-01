package com.banxia.api;

import com.banxia.api.response.Response;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 15:35
 */
public interface IAuthService {

    public Response<String> weixinQrCodeTicket();

    public Response<String> checkLogin(String ticket);
}
