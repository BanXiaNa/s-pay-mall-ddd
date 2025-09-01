package com.banxia.infrastructure.gateway;

import com.banxia.infrastructure.gateway.dto.WeixinQrCodeRequestDTO;
import com.banxia.infrastructure.gateway.dto.WeixinQrCodeResponseDTO;
import com.banxia.infrastructure.gateway.dto.WeixinTemplateMessageDTO;
import com.banxia.infrastructure.gateway.dto.WeixinTokenResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author BanXia
 * @description: 微信API服务
 * @Date 2025/8/12 16:25
 */
public interface IWeixinApiService {

    /**
     * 获取微信token
     * @param grantType 获取token的方式
     * @param appid 第三方应用的唯一凭证
     * @param secret 第三方的密钥
     * @return 响应结果
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getToken(
            @Query("grant_type") String grantType,
            @Query("appid") String appid,
            @Query("secret") String secret
    );

    /**
     * 创建二维码
     * @param accessToken 微信token
     * @param  weixinQrCodeRequestDTO 二维码参数
     * @return 响应结果
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrcode(
            @Query("access_token") String accessToken,
            @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO
    );

    /**
     * 发送模板消息
     * @param accessToken 获取token
     * @param weixinTemplateMessageDTO 模板消息参数
     * @return 响应结果
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(
            @Query("access_token") String accessToken,
            @Body WeixinTemplateMessageDTO weixinTemplateMessageDTO
            );
}
