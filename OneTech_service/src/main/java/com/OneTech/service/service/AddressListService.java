package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.common.vo.FriendListVO;
import com.OneTech.model.model.AddressListBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.vo.NewFriendVO;
import com.OneTech.common.vo.MomentsVO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AddressListService extends IBaseService<AddressListBean> {
    boolean sendVerification(JSONObject requestJson) throws Exception;

    List<NewFriendVO> getNewFriend(JSONObject requestJson) throws Exception;

    void addConfirm(JSONObject requestJson) throws Exception;

    List<FriendListVO> getFriendList(JSONObject requestJson) throws Exception;

    JSONObject getFriendListByPY(JSONObject requestJson) throws Exception;

    void deleteFriend(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsFriendList(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsByWechatId(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsPictureByWechatId(JSONObject requestJson) throws Exception;
}
