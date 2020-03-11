package com.OneTech.web.controller;

import com.OneTech.common.constants.controllerConstants.MainConstants;
import com.OneTech.common.util.JwtTokenUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.redis.core.RedisTemplate;
import com.OneTech.common.util.massageUtils.MassageUitls;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.vo.StatusBean;

@RestController
@RequestMapping(value = "/")
public class mainController extends CommonController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    MassageUitls massageUitls;

    /**
     * 获取验证码
     */
    @PostMapping("getVerifiCode")
    public StatusBean<?> getVerifiCode() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        massageUitls.sendMassageToSingle(getRequestJson());
        statusBean.setRespMsg(MainConstants.SEND_SUCCESS);
        statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        return statusBean;
    }

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping(value = "login")
    public StatusBean<?> login() throws Exception{
        return userInfoService.login(getRequestJson());
    }

    /**
     * 查询手机是否被使用
     *
     * @return
     */
    @PostMapping("checkPhoneUsed")
    public StatusBean<?> checkPhoneUsed() {
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            if (userInfoService.checkPhoneUsed(jsonObject)){
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(MainConstants.UNUSED_MSG);
            }else{
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(MainConstants.USED_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MainConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

}
