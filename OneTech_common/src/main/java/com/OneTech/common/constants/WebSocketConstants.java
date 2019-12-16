package com.OneTech.common.constants;

public class WebSocketConstants {

    /**
     * websocket连接地址
     */
    public final static String WEBSOCKET_URL = "/websocket/socketServer";

    /**
     * sockjs连接地址
     */
    public final static String JOCKJS_URL = "/sockjs/socketServer";

    /**
     * websocket 页面连接地址开头
     */
    public final static String WEBSOCKET_TAB = "tab";

    /**
     * websocket 聊天室连接地址开头
     */
    public final static String WEBSOCKET_CHATPAGE = "chatPage";
    /**
     *
     */
    public final static String ALLOWED_ORIGIN = "http://localhost:28180";
    /**
     * websocket属性名称
     */
    public final static String ATTRIBUTES_NAME = "WS_NAME";
    /**
     * 已经登陆提示
     */
    public final static String HAS_LOADING = "该账号已经登陆";
    /**
     * 离线消息分隔符
     */
    public final static String PAYLOAD_SPLIT = "#--#";
    /**
     * 发送消息分隔符
     */
    public final static String TO_SPLIT = "#";
}
