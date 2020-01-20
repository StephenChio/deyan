package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.model.UserLanguageBean;
import com.OneTech.service.service.UserLanguageService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("UserLanguageServiceImpl")
public class UserLanguageServiceImpl extends BaseServiceImpl<UserLanguageBean> implements UserLanguageService {
    @Override
    public void updateLanguageSetting(JSONObject requestJson) throws Exception {
        UserLanguageBean userLanguageBean = new UserLanguageBean();
        userLanguageBean.setWechatId(requestJson.getString("wechatId"));
        userLanguageBean = this.selectOne(userLanguageBean);
        if (BooleanUtils.isNotEmpty(userLanguageBean)) {
            userLanguageBean.setMotherLanguage(requestJson.getString("motherLanguage"));
            userLanguageBean.setFirstLanguage(requestJson.getString("firstLanguage"));
            userLanguageBean.setSecondLanguage(requestJson.getString("secondLanguage"));
            userLanguageBean.setThirdLanguage(requestJson.getString("thirdLanguage"));
            userLanguageBean.setUpdateTime(new Date());
            this.saveOrUpdate(userLanguageBean);
        } else {
            userLanguageBean = new UserLanguageBean();
            userLanguageBean.setId(UUIDUtils.getRandom32());
            userLanguageBean.setWechatId(requestJson.getString("wechatId"));
            userLanguageBean.setMotherLanguage(requestJson.getString("motherLanguage"));
            userLanguageBean.setFirstLanguage(requestJson.getString("firstLanguage"));
            userLanguageBean.setSecondLanguage(requestJson.getString("secondLanguage"));
            userLanguageBean.setThirdLanguage(requestJson.getString("thirdLanguage"));
            userLanguageBean.setCreateTime(new Date());
            this.save(userLanguageBean);
        }
    }

    @Override
    public UserLanguageBean getLanguageSetting(JSONObject requestJson) throws Exception {
        UserLanguageBean userLanguageBean = new UserLanguageBean();
        userLanguageBean.setWechatId(requestJson.getString("wechatId"));
        userLanguageBean = this.selectOne(userLanguageBean);
        return BooleanUtils.isEmpty(userLanguageBean) ? new UserLanguageBean() : userLanguageBean;
    }
}
