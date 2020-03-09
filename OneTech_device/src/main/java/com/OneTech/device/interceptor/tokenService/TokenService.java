package com.OneTech.device.interceptor.tokenService;

import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.util.redis.StringRedisTemplateUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class TokenService {
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
            int time = Integer.parseInt(StringRedisTemplateUtil.get(ip));
            if (time >= 60) {
                return false;
            } else {
                StringRedisTemplateUtil.set(ip, String.valueOf(time + 1), StringRedisTemplateUtil.ttl(ip).intValue());
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
            e.printStackTrace();
            System.out.println("解码出错,或者超时");
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
