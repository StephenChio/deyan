package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.ResourceBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ResourceService extends IBaseService<ResourceBean> {
    List<String> getPictureImgPath(String pictureId)throws Exception;
    List<String> get4MomentsImgByWechatId(JSONObject jsonObject)throws Exception;
}
