package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/")
public class mainController extends CommonController {
    @Autowired
    UserInfoService userInfoService;

    /**
     * 用户登录
     * @return
     */
    @PostMapping(value = "login")
    public StatusBean<?> login(){
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        UserInfoBean userInfo = new UserInfoBean();
        UserInfoBean userInfoBean;
        try {
            String phone = getRequestJson().getString("phone");
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
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("登陆失败!"+e);
            return statusBean;
        }
        statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        statusBean.setRespMsg("登陆成功!");
        statusBean.setData(userInfoBean);
        return statusBean;
    }
}
