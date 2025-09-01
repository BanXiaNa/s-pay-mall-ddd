package com.banxia.types.sdk.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信消息实体类
 */
@XStreamAlias("xml")
public class MessageTextEntity {

    // 接收者微信号
    @XStreamAlias("ToUserName")
    private String toUserName;

    // 发送者微信号
    @XStreamAlias("FromUserName")
    private String fromUserName;

    // 消息创建时间
    @XStreamAlias("CreateTime")
    private String createTime;

    // 消息类型
    @XStreamAlias("MsgType")
    private String msgType;

    // 事件类型
    @XStreamAlias("Event")
    private String event;

    // 事件KEY值
    @XStreamAlias("EventKey")
    private String eventKey;

    // 消息ID
    @XStreamAlias("MsgId")
    private String msgId;

    // 状态
    @XStreamAlias("Status")
    private String status;

    // 二维码
    @XStreamAlias("Ticket")
    private String ticket;

    // 内容
    @XStreamAlias("Content")
    private String content;

    // Getters and Setters
    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}