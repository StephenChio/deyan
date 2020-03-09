package com.OneTech.web.controller;

import com.OneTech.common.constants.controllerConstants.MainConstants;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.service.service.TestService;
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
    @Autowired
    TestService testService;

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
    public StatusBean<?> login() {
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
        try {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setPhone(getRequestJson().getString("phone"));
            userInfoBean = userInfoService.selectOne(userInfoBean);
            if (BooleanUtils.isEmpty(userInfoBean)) {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(MainConstants.UNUSED_MSG);
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(MainConstants.USED_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MainConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }


    @PostMapping(value = "test")
    public String test() {
        testService.print();
        String result = null;
        try {
            result = (String)testService.out().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        testService.print();
        testService.print();
        System.out.println(JwtTokenUtil.updateToken(getRequestJson()));
        System.out.println("结束");
        return result;
    }
}
