package com.OneTech.model.mapper;


import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;
import com.OneTech.common.vo.NewFriendVO;
import java.util.List;

public interface UserInfoMapper extends IBaseMapper<UserInfoBean> {
    /**
     *
     * @param wechatId,searchContext
     * @return
     * @throws Exception
     */
    List<UserInfoBean> searchFriend(JSONObject requestJson) throws Exception;
    List<UserInfoBean> getFriendList(JSONObject requestJson) throws Exception;
    List<NewFriendVO> getNewFriend(JSONObject requestJson) throws Exception;
    List<MomentsVO> getMomentsFriendList(JSONObject requestJson) throws Exception;
    List<MomentsVO> getMomentsByWechatId(JSONObject requestJson) throws Exception;
}
