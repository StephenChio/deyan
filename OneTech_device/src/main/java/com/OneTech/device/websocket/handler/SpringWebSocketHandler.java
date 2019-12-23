package com.OneTech.device.websocket.handler;

import com.OneTech.common.constants.WebSocketConstants;
import com.OneTech.device.websocket.interceptor.SpringWebSocketHandlerInterceptor;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Component;
import com.OneTech.common.util.BooleanUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * handler
 *
 * @author qww
 */
@Component
public class SpringWebSocketHandler extends TextWebSocketHandler {
    public static final Map<String, WebSocketSession> usersConnect = new HashMap<>();
    public static final ArrayList<String> OnlineUsers = new ArrayList<>();

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 当连接为新用户连接时添加新用户
     *
     * @param userName
     * @return
     */
    public boolean addOnlineUsers(String userName) {
        userName = userName.split("and")[1];
        for (Map.Entry<String, WebSocketSession> entry : usersConnect.entrySet()) {
            String connectName = (String) entry.getValue().getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME);
            connectName = connectName.split("and")[1];
            if (connectName.equals(userName)) {
                return false;
            }
        }
        OnlineUsers.add(userName);
        return true;
    }

    /**
     * 当该用户连接全部断开时删除用户
     *
     * @param userName
     * @return
     */
    public boolean removeOnlineUsers(String userName) {
        userName = userName.split("and")[1];
        for (Map.Entry<String, WebSocketSession> entry : usersConnect.entrySet()) {
            String connectName = (String) entry.getValue().getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME);
            connectName = connectName.split("and")[1];
            if (connectName.equals(userName)) {
                return false;
            }
        }
        OnlineUsers.remove(userName);
        return true;
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        //判断改连接之前是否存在，如果是，删除旧连接
        SpringWebSocketHandlerInterceptor.usersIdMap.put((String) session.getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME), session.getId());
        addOnlineUsers((String) session.getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME));
        usersConnect.put(session.getId(), session);
        System.out.println("用户当前数量:" + OnlineUsers.size());
        System.out.println("connect to the websocket success......当前数量:" + usersConnect.size());
//        for (Map.Entry<String, WebSocketSession> entry : usersConnect.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
//        }
        this.handleOutLineMessage(session);//处理该账号的离线消息
    }

    /**
     * 处理离线消息
     *
     * @param session
     */
    public void handleOutLineMessage(WebSocketSession session) {
        /**
         * 该账号是否有未收到消息
         */
        String userName = session.getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME).toString();
        if (BooleanUtils.isNotEmpty(redisTemplate.opsForValue().get(userName))) {
            try {
                String payload[] = redisTemplate.opsForValue().get(userName).split(WebSocketConstants.PAYLOAD_SPLIT);
                for (String load : payload) {
                    TextMessage message = new TextMessage(load);
                    if (load.startsWith("{\"to\":")) {//处理聊天消息
                        this.handleTextMessage(session, message);
                    } else {//处理其他消息
                        this.sendMessageToUser(userName, message, true);
                    }
                    System.out.println("发送离线消息成功" + load);
                }
                redisTemplate.delete(userName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("websocket connection closed......");
//        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
//        System.out.println("用户" + username + "已退出！");
//        SpringWebSocketHandlerInterceptor.usersIdMap.remove((String)session.getAttributes().get("WS_NAME"));
        usersConnect.remove(session.getId());
        removeOnlineUsers((String) session.getAttributes().get(WebSocketConstants.ATTRIBUTES_NAME));
        System.out.println("剩余在线用户" + OnlineUsers.size());
        System.out.println("剩余用户连接数" + usersConnect.size());
//            for (Map.Entry<String, WebSocketSession> entry : usersConnect.entrySet()) {
//                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
//            }
//
    }

    /**
     * js调用websocket.send时候，会调用该方法
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        JSONObject jsonObject = JSON.parseObject(message.getPayload());
//        this.sendMessageToUser(jsonObject.getString("fWechatId"),message);
        String[] to = jsonObject.getString("to").split(WebSocketConstants.TO_SPLIT);
        boolean isSuccess = false;
        for (String t : to) {
            if (t.equals(WebSocketConstants.WEBSOCKET_CHATPAGE)) {
                System.out.println("发送给" + t + "and" + jsonObject.getString("fWechatId") +  "and"  + jsonObject.getString("wechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t +  "and"  + jsonObject.getString("fWechatId") + "and" + jsonObject.getString("wechatId"), message, false);
            } else {
                System.out.println("发送给" + t +  "and"  + jsonObject.getString("fWechatId"));
                isSuccess = isSuccess || this.sendMessageToUser(t +"and"+ jsonObject.getString("fWechatId"), message, false);
            }
        }
        if (!isSuccess) {//保存聊天消息
            String userName = "tab1" +  "and"  + jsonObject.getString("fWechatId");
            if (BooleanUtils.isNotEmpty(redisTemplate.opsForValue().get(userName))) {//之前有消息
                String payload = redisTemplate.opsForValue().get(userName) + WebSocketConstants.PAYLOAD_SPLIT + message.getPayload();
                redisTemplate.opsForValue().set(userName, payload);
            } else {//之前没有消息
                redisTemplate.opsForValue().set(userName, message.getPayload());
            }
            System.out.println("对方不在线，消息已经保存");
        }
    }

    /**
     * 连接出现异常时触发
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("websocket connection closed......");
        usersConnect.remove(session.getId());
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给某个用户发送消息
     * boolean isSave 此条消息是否主动保存或者自定义保存
     *
     * @param userName
     * @param message
     */
    public boolean sendMessageToUser(String userName, TextMessage message, boolean isSave) {
        String userId = SpringWebSocketHandlerInterceptor.usersIdMap.get(userName);
        if (isSave) {
            if (userId == null) {
                if (BooleanUtils.isNotEmpty(redisTemplate.opsForValue().get(userName))) {//之前有消息
                    String payload = redisTemplate.opsForValue().get(userName) + WebSocketConstants.PAYLOAD_SPLIT + message.getPayload();
                    redisTemplate.opsForValue().set(userName, payload);
                } else {//之前没有消息
                    redisTemplate.opsForValue().set(userName, message.getPayload());
                }
                System.out.println("对方不在线，消息已经保存");
                return false;
            }
        }
        WebSocketSession webSocketSession = usersConnect.get(userId);
        if (webSocketSession != null) {
            try {
                webSocketSession.sendMessage(message);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            for (Map.Entry<String, WebSocketSession> entry : usersConnect.entrySet()) {
                webSocketSession = entry.getValue();
                webSocketSession.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}