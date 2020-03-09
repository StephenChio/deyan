package com.OneTech.device.interceptor.handler;

import com.OneTech.device.interceptor.tokenService.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpringInterceptorHandler extends HandlerInterceptorAdapter {

    @Autowired
    TokenService tokenService;

    /**
     * 进入controller前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 先检验身份
         * token是否过期
         * 然后查询该IP的访问频率大于60次/秒 则不接受请求
         */
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        if (url.startsWith("/img")) return true;
//        System.out.println(url);
        return tokenService.testIp(ip) && ("/idSalt/getSalt".equals(url) || "/login".equals(url) || "/getVerifiCode".equals(url)) ? true : tokenService.testToken(request);
    }

    public SpringInterceptorHandler() {
    }
}
