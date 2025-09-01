package com.banxia.domain.auth.service.impl;

import com.banxia.domain.auth.adapter.port.ILoginPort;
import com.banxia.domain.auth.service.ILoginService;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 11:36
 */
@Service
@Slf4j
public class WeixinLoginService implements ILoginService {

    @Resource
    private ILoginPort loginPort;
    // 缓存openid
    @Resource
    private Cache<String , String> openidToken;

    /**
     * 创建微信登录二维码
     * @return 二维码ticket
     * @throws Exception 创建二维码异常
     */
    @Override
    public String createQrCodeTicket() throws Exception {
        return loginPort.createQrCodeTicket();
    }

    @Override
    public String checkLogin(String ticket) throws Exception {
        return openidToken.getIfPresent(ticket);
    }

    @Override
    public void saveLoginState(String ticket, String openId) throws Exception {
        //
        openidToken.put(ticket, openId);

        loginPort.sendLoginTemplate(openId);
    }
}
