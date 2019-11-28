package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.ResourceBean;

import java.util.List;

public interface ResourceService extends IBaseService<ResourceBean> {
    List<String> getPictureImgPath(String pictureId)throws Exception;
}
