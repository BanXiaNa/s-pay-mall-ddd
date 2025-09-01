package com.banxia.trigger.http;

import com.banxia.domain.auth.service.ILoginService;
import com.banxia.types.sdk.weixin.MessageTextEntity;
import com.banxia.types.sdk.weixin.SignatureUtil;
import com.banxia.types.sdk.weixin.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 微信服务对接，对接地址：<a href="http://xfg-studio.natapp1.cc/api/v1/weixin/portal/receive">/api/v1/weixin/portal/receive</a>
 * <p>
 * http://banxia.natapp1.cc/api/v1/weixin/portal/receive/
 */
@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/weixin/portal/")
public class WeixinPortalController {

    /**
     * 公众号拥有者ID
     * 由微信平台发放
     */
    @Value("${weixin.config.originalid}")
    private String originalid;
    /**
     * 微信公众号的token
     * 用户配置
     */
    @Value("${weixin.config.token}")
    private String token;

    @Resource
    private ILoginService loginService;

    /**
     * 验证公众号信息
     * 为什么需要验证：确保这个后端是正确的后端，避免被劫持，我们使用公众号拥有者ID和用户自定的Token加密后进行验证
     *  确保这个本地运行的后端程序和微信平台对接成功
     * 原理就是本地计算哈希码，和微信平台提供的token进行比较
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return 验证结果
     */
    @GetMapping(value = "receive", produces = "text/plain;charset=utf-8")
    public String validate(@RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            log.info("微信公众号验签信息开始 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = SignatureUtil.check(token, signature, timestamp, nonce);
            log.info("微信公众号验签信息完成 check：{}", check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息失败 [{}, {}, {}, {}]", signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    @PostMapping(value = "receive", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            log.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);

            if("event".equals(message.getMsgType()) && "SCAN".equals(message.getEvent())){
                loginService.saveLoginState(message.getTicket(), openid);
                return buildMessageTextEntity(openid, "登录成功");
            }


            return buildMessageTextEntity(openid, "你好，" + message.getContent());
        } catch (Exception e) {
            log.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }

    /**
     * 构建回复消息
     * @param openid 公众号用户的openid
     * @param content 回复内容
     * @return 回复消息
     */
    private String buildMessageTextEntity(String openid, String content) {
        MessageTextEntity res = new MessageTextEntity();
        // 公众号分配的ID
        res.setFromUserName(originalid);
        res.setToUserName(openid);
        res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
        res.setMsgType("text");
        res.setContent(content);
        return XmlUtil.beanToXml(res);
    }

    /**
     * 调试接口 - 用于接收微信回调的所有参数并记录
     * 测试方式；
     * 1. 修改 receive/debug 为 receive，另外一个 receive 修改为 receive2
     * 2. debug 调试运行，看 requestParams 的入参信息
     */
    @PostMapping(value = "receive/debug", produces = "application/xml; charset=UTF-8")
    public String debugParams(@RequestBody String requestBody,
                             @RequestParam Map<String, String> requestParams) {
        try {
            // 记录所有请求参数
            log.info("微信调试接口 - 请求参数Map: {}", requestParams);
            log.info("微信调试接口 - 请求体: {}", requestBody);

            // 消息转换
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
            return buildMessageTextEntity(requestParams.get("openid"), "你好，" + message.getContent());

        } catch (Exception e) {
            log.error("微信调试接口处理失败", e);
            return "";
        }
    }

}
