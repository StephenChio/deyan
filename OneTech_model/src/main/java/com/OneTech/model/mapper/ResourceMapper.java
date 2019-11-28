package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.model.model.ResourceBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper extends IBaseMapper<ResourceBean> {
    List<String> getPictureImgPath(@Param("PICTURE_ID") String prictureId);
}
