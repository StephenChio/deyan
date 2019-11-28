package com.OneTech.device.websocket.handler;


import com.OneTech.common.constants.SystemConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnMessage;

/**
 * handler
 *
 * @author huangminjiang
 */
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    private static final ArrayList<WebSocketSession> users;//这个会出现性能问题，最好用Map来存储，key用userid

    @Autowired
    RedisTemplate redisTemplate;

    //    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    static {
        users = new ArrayList<WebSocketSession>();
    }

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        //判断改连接之前是否存在，如果是，删除旧连接
        String userName = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        users.remove(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        users.add(session);
        System.out.println("connect to the websocket success......当前数量:" + users.size());
        for (WebSocketSession user : users) {
            System.out.println(user);
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
        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        System.out.println("用户" + username + "已退出！");
        users.remove(session);
        System.out.println("剩余在线用户" + users.size());
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
        for(String t : to) {
            if(t.equals("chatPage")){
                System.out.println("发送给"+t+jsonObject.getString("fWechatId")+jsonObject.getString("wechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t + jsonObject.getString("fWechatId")+jsonObject.getString("wechatId"), message);
            }
            else {
                System.out.println("发送给"+t+jsonObject.getString("fWechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t + jsonObject.getString("fWechatId"), message);
            }
        }
        if(!isSuccess){
            TextMessage returnMessage;
            returnMessage = new TextMessage(SystemConstants.RESPONSE_FAIL);
            this.sendMessageToUser(to[1]+jsonObject.getString("wechatId"),returnMessage);
        }
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("websocket connection closed......");
        users.remove(session);
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
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                        return true;
                    }
                    else{
                        System.out.println("对方不在线，消息已经保存");
                        redisTemplate.opsForValue().set(user,message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                break;
            }
        }
        System.out.println("对方不在线，消息已经保存");
        redisTemplate.opsForValue().set(userName,message);
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}