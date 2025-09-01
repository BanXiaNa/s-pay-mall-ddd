package com.banxia.infrastructure.adapter.port;

import com.banxia.domain.auth.adapter.port.ILoginPort;
import com.banxia.infrastructure.gateway.IWeixinApiService;
import com.banxia.infrastructure.gateway.dto.WeixinQrCodeRequestDTO;
import com.banxia.infrastructure.gateway.dto.WeixinQrCodeResponseDTO;
import com.banxia.infrastructure.gateway.dto.WeixinTemplateMessageDTO;
import com.banxia.infrastructure.gateway.dto.WeixinTokenResponseDTO;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/9/1 11:47
 */
@Service
public class  LoginPort implements ILoginPort {

    @Value("${weixin.config.app-id}")
    private String appId;
    @Value("${weixin.config.app-secret}")
    private String appSecret;
    @Value("${weixin.config.template-id}")
    private String templateId;

    @Resource
    private Cache<String , String> weixinAccessToken;
    @Resource
    private IWeixinApiService weixinApiService;

    @Override
    public String createQrCodeTicket() throws IOException {
        // 获取AccessToken
        // 首先检测缓存
        String accessToken = weixinAccessToken.getIfPresent(appId);
        if(null == accessToken){
            // 没有，就请求
            Call<WeixinTokenResponseDTO> callWeixinToken = weixinApiService.getToken("client_credential", appId, appSecret);
            WeixinTokenResponseDTO weixinTokenResponseDTO = callWeixinToken.execute().body();
            assert weixinTokenResponseDTO != null;
            accessToken = weixinTokenResponseDTO.getAccess_token();
            weixinAccessToken.put(appId, accessToken);
        }

        //      // 现在获取二维码
        WeixinQrCodeRequestDTO weixinQrCodeRequestDTO = WeixinQrCodeRequestDTO.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrCodeRequestDTO.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeRequestDTO.ActionInfo.builder()
                        .scene(WeixinQrCodeRequestDTO.ActionInfo.Scene.builder()
                                .scene_id(100601)
                                .build())
                        .build())
                .build();

        Call<WeixinQrCodeResponseDTO> callWeixinQrCode = weixinApiService.createQrcode(accessToken, weixinQrCodeRequestDTO);
        WeixinQrCodeResponseDTO weixinQrCodeRes = callWeixinQrCode.execute().body();
        assert weixinQrCodeRes != null;

        return weixinQrCodeRes.getTicket();
    }

    @Override
    public void sendLoginTemplate(String openId) throws IOException {
        String accessToken = weixinAccessToken.getIfPresent(appId);


        if(null == accessToken){
            // 没有，就请求
            Call<WeixinTokenResponseDTO> callWeixinToken = weixinApiService.getToken("client_credential", appId, appSecret);
            WeixinTokenResponseDTO weixinTokenResponseDTO = callWeixinToken.execute().body();
            assert weixinTokenResponseDTO != null;
            accessToken = weixinTokenResponseDTO.getAccess_token();
            weixinAccessToken.put(appId, accessToken);
        }

        // 发送模板消息
        Map<String, Map<String, String>> data = new HashMap<>();
        WeixinTemplateMessageDTO.put(data, WeixinTemplateMessageDTO.TemplateKey.USER, openId);

        WeixinTemplateMessageDTO templateMessageDTO = new WeixinTemplateMessageDTO(openId, templateId);
        templateMessageDTO.setUrl("https://gaga.plus");
        templateMessageDTO.setData(data);

        Call<Void> call = weixinApiService.sendMessage(accessToken, templateMessageDTO);
        call.execute();


    }
}
