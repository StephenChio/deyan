package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.UserLanguageConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.UserLanguageBean;
import com.OneTech.service.service.UserLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("userLanguage")
public class userLanguageController extends CommonController {
    @Autowired
    UserLanguageService userLanguageService;
    @PostMapping("updateLanguageSetting")
    public StatusBean<?> updateLanguageSetting() {
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            userLanguageService.updateLanguageSetting(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserLanguageConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserLanguageConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getLanguageSetting")
    public StatusBean<?> getLanguageSetting() {
        StatusBean<UserLanguageBean> statusBean = new StatusBean<>();
        try {
            statusBean.setData(userLanguageService.getLanguageSetting(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(UserLanguageConstants.SETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserLanguageConstants.SETTING_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
