package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.LoginVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface UserInfoService extends IBaseService<UserInfoBean> {
    List<UserInfoBean> searchFriend(JSONObject requestJson) throws Exception;

    UserInfoBean updateName(JSONObject requestJson) throws Exception;

    UserInfoBean updatePicture(JSONObject requestJson) throws Exception;

    UserInfoBean updateBackgroundImg(JSONObject requestJson) throws Exception;

    boolean changePhoneNum(JSONObject requestJson) throws Exception;

    boolean updatePassword(JSONObject requestJson) throws Exception;

    LoginVO initUser(String phone) throws Exception;

    void addRobot(String wechatId)throws Exception;

    String getUserNameByWechatId(String wechatId) throws Exception;

    StatusBean<?> login(JSONObject requestJson);

    StatusBean<?> passwordLogin(JSONObject requestJson);

    StatusBean<?> verifiCodeLogin(JSONObject requestJson);

    void updateSex(JSONObject requestJson) throws Exception;

    void updateSign(JSONObject requestJson) throws Exception;

    void updatePosition(JSONObject requestJson) throws Exception;

    String getDate(JSONObject requestJson) throws Exception;

    Boolean checkPhoneUsed(JSONObject requestJson)throws Exception;

}
