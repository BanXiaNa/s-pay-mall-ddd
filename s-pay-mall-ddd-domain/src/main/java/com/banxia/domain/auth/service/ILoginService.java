package com.banxia.domain.auth.service;

/**
 * @Author BanXia
 * @description: 登录服务
 * @Date 2025/8/13 20:31
 */
public interface ILoginService {

    /**
     * 创建二维码
     * @return 二维码ticket
     * @throws Exception 创建二维码异常
     */
    String createQrCodeTicket() throws Exception;

    /**
     * 通过提交的ticket查询登录状态
     * @param ticket 提交ticket
     * @return 登录状态
     * @throws Exception 查询登录状态异常
     */
    String checkLogin(String ticket) throws Exception;


    /**
     * 通过ticket和openId保存登录状态
     * @param ticket 提交ticket
     * @param openId 提交openId
     * @throws Exception 保存登录状态异常
     */
    void saveLoginState(String ticket, String openId) throws Exception;
}
