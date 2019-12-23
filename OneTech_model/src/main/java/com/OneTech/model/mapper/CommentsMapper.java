package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.vo.FriendListVO;
import com.OneTech.model.model.CommentsBean;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommentsMapper extends IBaseMapper<CommentsBean> {
    List<FriendListVO> selectLike(JSONObject jsonObject);
}
