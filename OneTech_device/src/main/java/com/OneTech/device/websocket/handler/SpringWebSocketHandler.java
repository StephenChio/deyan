package com.OneTech.device.websocket.handler;


import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.device.websocket.interceptor.SpringWebSocketHandlerInterceptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * handler
 *
 * @author huangminjiang
 */
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    public static final Map<String, WebSocketSession> users = new HashMap<>();

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        //判断改连接之前是否存在，如果是，删除旧连接
        SpringWebSocketHandlerInterceptor.usersIdMap.put((String) session.getAttributes().get("WS_NAME"), session.getId());
        users.put(session.getId(), session);
        System.out.println("connect to the websocket success......当前数量:" + users.size());
        for (Map.Entry<String, WebSocketSession> entry : users.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
//        String username= (String) session.getAttributes().get("WEBSOCKET_USERNAME");
//        TextMessage returnMessage = new TextMessage("收到消息");
//        this.sendMessageToUser(username,returnMessage);
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("websocket connection closed......");
//        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
//        System.out.println("用户" + username + "已退出！");
//        SpringWebSocketHandlerInterceptor.usersIdMap.remove((String)session.getAttributes().get("WS_NAME"));
        users.remove(session.getId());
        System.out.println("剩余在线用户" + users.size());
        for (Map.Entry<String, WebSocketSession> entry : users.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        JSONObject jsonObject = JSON.parseObject(message.getPayload());
//        this.sendMessageToUser(jsonObject.getString("fWechatId"),message);
        String[] to = jsonObject.getString("to").split("#");
        boolean isSuccess = false;
        for (String t : to) {
            if (t.equals("chatPage")) {
                System.out.println("发送给" + t + jsonObject.getString("fWechatId") + jsonObject.getString("wechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t + jsonObject.getString("fWechatId") + jsonObject.getString("wechatId"), message);
            } else {
                System.out.println("发送给" + t + jsonObject.getString("fWechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t + jsonObject.getString("fWechatId"), message);
            }
        }
        if (!isSuccess) {
            TextMessage returnMessage;
            returnMessage = new TextMessage(SystemConstants.RESPONSE_FAIL);
            this.sendMessageToUser(to[1] + jsonObject.getString("wechatId"), returnMessage);
        }
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("websocket connection closed......");
        users.remove(session.getId());
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public boolean sendMessageToUser(String userName, TextMessage message) {
        String userId = SpringWebSocketHandlerInterceptor.usersIdMap.get(userName);
        if(userId==null){
            System.out.println("对方不在线，消息已经保存");
            return false;
        }
        WebSocketSession webSocketSession = users.get(userId);
        if(webSocketSession!=null) {
            try {
                webSocketSession.sendMessage(message);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("对方不在线，消息已经保存");
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        WebSocketSession webSocketSession;
        try {
            for (Map.Entry<String, WebSocketSession> entry : users.entrySet()) {
                webSocketSession = entry.getValue();
                webSocketSession.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}