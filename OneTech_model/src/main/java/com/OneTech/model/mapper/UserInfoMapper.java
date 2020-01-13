package com.OneTech.model.mapper;


import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.vo.FriendListVO;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;
import com.OneTech.common.vo.NewFriendVO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserInfoMapper extends IBaseMapper<UserInfoBean> {
    List<UserInfoBean> searchFriend(JSONObject requestJson) throws Exception;

    List<FriendListVO> getFriendList(JSONObject requestJson) throws Exception;

    List<NewFriendVO> getNewFriend(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsFriendList(JSONObject requestJson) throws Exception;

    List<MomentsVO>  getMomentById(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsByWechatId(JSONObject requestJson) throws Exception;

    List<MomentsVO> getMomentsPictureByWechatId(JSONObject requestJson) throws Exception;
}
