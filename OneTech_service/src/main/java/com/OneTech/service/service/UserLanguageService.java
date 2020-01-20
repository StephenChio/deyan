package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.UserLanguageBean;
import com.alibaba.fastjson.JSONObject;

public interface UserLanguageService  extends IBaseService<UserLanguageBean> {
    void updateLanguageSetting(JSONObject requestJson)throws Exception;
    UserLanguageBean getLanguageSetting(JSONObject requestJson)throws Exception;
}
