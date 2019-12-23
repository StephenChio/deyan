package com.OneTech.web.controller;

import com.OneTech.common.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.redis.core.RedisTemplate;
import com.OneTech.common.util.massageUtils.MassageUitls;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.common.util.BooleanUtils;
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
        if (massageUitls.sendMassageToSingle(getRequestJson())) {
            statusBean.setRespMsg("发送成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } else {
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
        StatusBean<LoginVO> statusBean = new StatusBean<>();
        UserInfoBean userInfo = new UserInfoBean();
        LoginVO loginVO = new LoginVO();
        try {
            String phone = getRequestJson().getString("phone");
            String loginType = getRequestJson().getString("loginType");
            if ("password".equals(loginType)) {
                String password = getRequestJson().getString("password");
                userInfo.setPhone(phone);
                userInfo.setPassWord(password);
                userInfo = userInfoService.selectOne(userInfo);
                if (userInfo == null) {
                    statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                    statusBean.setRespMsg("密码不正确");
                    return statusBean;
                } else {
                    /**
                     * 构造登陆返回对象
                     */
                    loginVO.setWechatId(userInfo.getWechatId());
                    loginVO.setBackgroundImg(userInfo.getBackgroundImg());
                    loginVO.setImgPath(userInfo.getImgPath());
                    loginVO.setPhone(userInfo.getPhone());
                    loginVO.setUserName(userInfo.getUserName());
                    if(BooleanUtils.isEmpty(userInfo.getPassWord())){
                        loginVO.setHasPassword(false);
                    }
                    else{
                        loginVO.setHasPassword(true);
                    }
                    statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                    statusBean.setRespMsg("登陆成功!");
                    statusBean.setData(loginVO);
                    return statusBean;
                }
            }
            String verifiCode = getRequestJson().getString("verifiCode");
            if (BooleanUtils.isEmpty(redisTemplate.opsForValue().get(phone))) {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("请重新发送验证码");
                return statusBean;
            }
            if (redisTemplate.opsForValue().get(phone).toString().equals(verifiCode)) {
                userInfo.setPhone(phone);
                userInfo = userInfoService.selectOne(userInfo);
                if (userInfo == null) {
                    loginVO = userInfoService.initUser(phone);
                }
                else {
                    /**
                     * 构造登陆返回对象
                     */
                    loginVO.setWechatId(userInfo.getWechatId());
                    loginVO.setBackgroundImg(userInfo.getBackgroundImg());
                    loginVO.setImgPath(userInfo.getImgPath());
                    loginVO.setPhone(userInfo.getPhone());
                    loginVO.setUserName(userInfo.getUserName());
                    if(BooleanUtils.isEmpty(userInfo.getPassWord())){
                        loginVO.setHasPassword(false);
                    }
                    else{
                        loginVO.setHasPassword(true);
                    }
                }
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg("登陆成功!");
                statusBean.setData(loginVO);
            } else {
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
            if (userInfoBean == null) {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg("该手机未被使用");
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("该手机已被使用");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("查询!" + e);
        }
        return statusBean;
    }
}
