package com.OneTech.device.fliter.handler;

import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.HttpServletUtils;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.util.redis.StringRedisTemplateUtil;
import com.OneTech.common.vo.LoginVO;
import com.OneTech.common.vo.token.UserTokenVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class SpringFliterHandler extends HandlerInterceptorAdapter {
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
         * 然后查询该IP的访问频率大于30次/秒 则不接受请求
         */
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        if (url.startsWith("/img")) return true;
        return testIp(ip) && ("/login".equals(url) || "/getVerifiCode".equals(url)) ? true : testToken(request);
    }

    public SpringFliterHandler() {
        super();
    }

    public boolean testIp(String ip) {
        if (ip.split(":").length > 1) {
            String temp[] = ip.split(":");
            ip = "";
            for (String t : temp) {
                ip = ip + "." + t;
            }
            ip = ip.substring(1, ip.length());
        }
        if (StringRedisTemplateUtil.exists(ip)) {
            if (Integer.parseInt(StringRedisTemplateUtil.get(ip)) >= 1000) {
                return false;
            } else {
                StringRedisTemplateUtil.set(ip, String.valueOf(Integer.parseInt(StringRedisTemplateUtil.get(ip)) + 1), 0);
            }
        } else {
            StringRedisTemplateUtil.set(ip, "1", 60);
        }
        return true;
    }

    /**
     * 验证token准确性
     * 更新token
     * @return
     */
    public boolean testToken(HttpServletRequest request) {
        String token = getRequestJson(request).getString("token");
        if (BooleanUtils.isEmpty(token)) return false;
        try {
            Object object = JwtTokenUtil.serializeToObject(token);
            if (BooleanUtils.isEmpty(object)) return false;
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }


    public JSONObject getRequestJson(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, String[]> requestMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
                String name = entry.getKey();
                Object value = request.getParameter(name);
                if (null != value) {
                    jsonObject.put(name, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
