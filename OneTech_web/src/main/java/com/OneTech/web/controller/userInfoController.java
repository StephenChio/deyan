package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userInfo")
public class userInfoController extends CommonController {
    @Autowired
    UserInfoService userInfoService;

    @PostMapping("searchFriend")
    public StatusBean<?> searchFriend() {
        StatusBean<List<UserInfoBean>> statusBean = new StatusBean<>();
        try {
            List<UserInfoBean> userinfos = userInfoService.searchFriend(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("搜索成功");
            if (userinfos != null) statusBean.setData(userinfos);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("搜索异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updateName")
    public StatusBean<?> updateName() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            statusBean.setData(userInfoService.updateName(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("设置异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updatePicture")
    public StatusBean<?> updatePicture() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            statusBean.setData(userInfoService.updatePicture(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("设置异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updateBackgroundImg")
    public StatusBean<?> updateBackgroundImg() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            statusBean.setData(userInfoService.updateBackgroundImg(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("设置成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("设置异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("changePhoneNum")
    public StatusBean<?> changePhoneNum() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            if (userInfoService.changePhoneNum(getRequestJson())) {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg("设置成功");
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("验证码错误或过期,请重新发送验证码");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("设置异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updatePassword")
    public StatusBean<?> updatePassword() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            if (!userInfoService.updatePassword(getRequestJson())) {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("旧密码错误!");
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg("设置成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("设置异常!" + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

}
