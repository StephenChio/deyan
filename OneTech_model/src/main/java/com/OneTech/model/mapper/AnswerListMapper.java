package com.OneTech.model.mapper;

import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.model.model.AnswerListBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AnswerListMapper extends IBaseMapper<AnswerListBean> {
    List<AnswerListVO> getAnswerListVOByQuestionId(@Param("ID") String id);

    AnswerListVO getAnswerById(JSONObject requestJson);

    String getNextAnswerId(JSONObject requestJson);

    String getPreAnswerId(JSONObject requestJson);
}
