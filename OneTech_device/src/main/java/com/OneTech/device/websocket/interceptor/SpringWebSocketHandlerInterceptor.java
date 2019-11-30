package com.OneTech.device.websocket.interceptor;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket拦截器
 * @author huangminjiang
 *
 */
public class SpringWebSocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {
    public static final Map<String,String> usersIdMap = new HashMap<>();//这个会出现性能问题，最好用Map来存储，key用userid
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {

            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String userName = servletRequest.getServletRequest().getParameter("WS_NAME");
            HttpSession session = servletRequest.getServletRequest().getSession(true);
            if (session != null) {
                //使用userName区分WebSocketHandler，以便定向发送消息
//                String userName = (String) session.getAttribute("SESSION_USERNAME");
//                if (userName==null) {
//                    userName="default-system";
//                }
                dealWithConnectioned(session,userName);
                map.put("WS_NAME", userName);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, map);

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception ex) {
        // TODO Auto-generated method stub
        super.afterHandshake(request, response, wsHandler, ex);
    }
    /**
     * 处理多设备登陆
     *
     */
    public static boolean dealWithConnectioned(HttpSession session,String userName) {
        Boolean isConnectioned = false;
        if(usersIdMap.containsKey(userName)){//该账号已经登陆
            Map<String, WebSocketSession> users = SpringWebSocketHandler.users;
            String userId = usersIdMap.get(userName);
            if (null != users) {
                if (users.containsKey(userId)) {
                    System.out.println("该账号已经登陆");
                    // 如果已经登录过，则推送"已登录过的提示"到客户端;
                    // 客户端接受信息，请求退出系统的接口;
                    // 清除session中保存的用户信息;
                    WebSocketSession webSocketSession = users.get(userId);
                    TextMessage promptMsg = new TextMessage(SystemConstants.NOT_ALLOW_LOGIN);
                    try {
                        webSocketSession.sendMessage(promptMsg);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // 从已登录用户列表中清除已经登录过的用户的信息记录;
                    users.remove(userId);
                    isConnectioned = true;
                }
            }
        }
        return isConnectioned;
    }
}