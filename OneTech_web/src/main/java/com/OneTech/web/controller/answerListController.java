package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.AnswerListConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.AnswerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answerList")
public class answerListController extends CommonController {
    @Autowired
    AnswerListService answerListService;
    @PostMapping("answerPublish")
    public StatusBean<?> answerPublish(){
        StatusBean<Boolean> statusBean = new StatusBean();
        try{
            answerListService.answerPublish(getRequestJson());
            statusBean.setRespMsg(AnswerListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(AnswerListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getAnswerById")
    public StatusBean<?> getAnswerById(){
        StatusBean<AnswerListVO> statusBean = new StatusBean();
        try{
            statusBean.setData(answerListService.getAnswerById(getRequestJson()));
            statusBean.setRespMsg(AnswerListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(AnswerListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
