package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.util.massageUtils.MassageUitls;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Date;


@RestController
@RequestMapping(value = "/")
public class mainController extends CommonController {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MassageUitls massageUitls;
    /**
     * 获取验证码
     */
    @PostMapping("getVerifiCode")
    public StatusBean<?> getVerifiCode() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        if(massageUitls.sendMassageToSingle(getRequestJson())){
            statusBean.setRespMsg("发送成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }
        else{
            statusBean.setRespMsg("发送失败");
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        return statusBean;
    }

    /**
     * 用户登录
     *
     * @return
     */
    @PostMapping(value = "login")
    public StatusBean<?> login() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        UserInfoBean userInfo = new UserInfoBean();
        UserInfoBean userInfoBean;
        try {
            String phone = getRequestJson().getString("phone");
            String loginType = getRequestJson().getString("loginType");
            if("password".equals(loginType)){
                String password = getRequestJson().getString("password");
                userInfo.setPhone(phone);
                userInfo.setPassWord(password);
                userInfoBean = userInfoService.selectOne(userInfo);
                if (userInfoBean == null) {
                    statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                    statusBean.setRespMsg("密码不正确");
                    return statusBean;
                }else{
                    statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                    statusBean.setRespMsg("登陆成功!");
                    statusBean.setData(userInfoBean);
                    return statusBean;
                }
            }
            String verifiCode = getRequestJson().getString("verifiCode");
            if(BooleanUtils.isEmpty(redisTemplate.opsForValue().get(phone))){
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("请重新发送验证码");
                return statusBean;
            }
            if(redisTemplate.opsForValue().get(phone).toString().equals(verifiCode)) {
                userInfo.setPhone(phone);
                userInfoBean = userInfoService.selectOne(userInfo);
                if (userInfoBean == null) {
                    userInfo.setId(UUIDUtils.getRandom32());
                    userInfo.setUserName("新用户");
                    userInfo.setWechatId(UUIDUtils.getRandom32());
                    userInfo.setMomentsId(UUIDUtils.getRandom32());
                    userInfo.setImgPath("img/head.png");
                    userInfo.setBackgroundImg("img/background.png");
                    userInfo.setCreateTime(new Date());
                    userInfoService.save(userInfo);
                    userInfoBean = userInfo;
                }
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg("登陆成功!");
                statusBean.setData(userInfoBean);
            }
            else{
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("验证码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("登陆失败!" + e);
        }
        return statusBean;
    }
}
