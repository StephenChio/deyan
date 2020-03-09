package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.UserInfoConstants;
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
            statusBean.setRespMsg(UserInfoConstants.SEARCH_SUCCESS);
            if (userinfos != null) statusBean.setData(userinfos);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SEARCH_FAIL + e);
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
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
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
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
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
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
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
                statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(UserInfoConstants.CODE_ERROR_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
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
                statusBean.setRespMsg(UserInfoConstants.OLD_PASS_ERROR_MSG);
            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updateSex")
    public StatusBean<?> updateSex() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            userInfoService.updateSex(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("updateSign")
    public StatusBean<?> updateSign() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            userInfoService.updateSign(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("updatePosition")
    public StatusBean<?> updatePosition() {
        StatusBean<UserInfoBean> statusBean = new StatusBean<>();
        try {
            userInfoService.updatePosition(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserInfoConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("getDate")
    public StatusBean<?> getDate() {
        StatusBean<String> statusBean = new StatusBean<>();
        try {
            statusBean.setData(userInfoService.getDate(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserInfoConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.QUERY_FAIL+ e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
