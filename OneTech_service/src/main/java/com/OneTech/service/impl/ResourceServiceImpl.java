package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.model.mapper.ResourceMapper;
import com.OneTech.model.model.ResourceBean;
import com.OneTech.service.service.ResourceService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ResourceServiceImpl")
public class ResourceServiceImpl extends BaseServiceImpl<ResourceBean> implements ResourceService {
    @Autowired
    ResourceMapper resourceMapper;
    @Override
    public List<String> getPictureImgPath(String pictureId) throws Exception {
        return resourceMapper.getPictureImgPath(pictureId);
    }

    @Override
    public List<String> get4MomentsImgByWechatId(JSONObject jsonObject) throws Exception {
        return resourceMapper.get4MomentsImgByWechatId(jsonObject);
    }
}
