package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.model.model.ResourceBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceMapper extends IBaseMapper<ResourceBean> {
    List<String> getPictureImgPath(@Param("PICTURE_ID") String prictureId);

    List<String> get4MomentsImgByWechatId(JSONObject jsonObject);
}
