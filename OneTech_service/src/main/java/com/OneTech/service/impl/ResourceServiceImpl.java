package com.OneTech.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.service.service.ResourceService;
import com.OneTech.model.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import com.OneTech.model.model.ResourceBean;
import com.alibaba.fastjson.JSONObject;
import java.util.List;


@Service("ResourceServiceImpl")
public class ResourceServiceImpl extends BaseServiceImpl<ResourceBean> implements ResourceService {
    @Autowired
    ResourceMapper resourceMapper;

    /**
     * 获得图片资源路径
     * @param pictureId
     * @return
     * @throws Exception
     */
    @Override
    public List<String> getPictureImgPath(String pictureId) throws Exception {
        return resourceMapper.getPictureImgPath(pictureId);
    }

    /**
     * 获取头4张朋友圈图片，用于个人信息页面展示
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public List<String> get4MomentsImgByWechatId(JSONObject jsonObject) throws Exception {
        return resourceMapper.get4MomentsImgByWechatId(jsonObject);
    }
}
