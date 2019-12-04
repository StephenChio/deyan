package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.model.model.CommentsBean;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface CommentsMapper extends IBaseMapper<CommentsBean> {
    List<UserInfoBean> selectLike(JSONObject jsonObject);
}
