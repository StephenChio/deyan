package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.model.mapper.ResourceMapper;
import com.OneTech.model.model.ResourceBean;
import com.OneTech.service.service.ResourceService;
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
}
