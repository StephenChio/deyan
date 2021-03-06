package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.QuestionListConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.QuestionListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questionList")
public class questionListController extends CommonController {
    @Autowired
    QuestionListService questionListService;
    @PostMapping("questionPublish")
    public StatusBean<?> questionPublish(){
        StatusBean<?> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            questionListService.questionPublish(jsonObject);
            statusBean.setRespMsg(QuestionListConstants.PUBLISH_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.PUBLISH_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("getMyQuestion")
    public StatusBean<?> getMyQuestion(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getMyQuestion(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getAllQuestionListByLanguageOption")
    public StatusBean<?> getAllQuestionListByLanguageOption(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getAllQuestionListByLanguageOption(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getAllQuestionList")
    public StatusBean<?> getAllQuestionList(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getAllQuestionList());
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getQuestionInformationById")
    public StatusBean<?> getQuestionInformationById(){
        StatusBean<QuestionListVO> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getQuestionInformationById(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getMyFollow")
    public StatusBean<?> getMyFollow(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getMyFollow(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("getMyAnswer")
    public StatusBean<?> getMyAnswer(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(questionListService.getMyAnswer(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("getMyCollect")
    public StatusBean<?> getMyCollect() {
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(questionListService.getMyCollect(jsonObject));
            statusBean.setRespMsg(QuestionListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(QuestionListConstants.QUERY_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

}
