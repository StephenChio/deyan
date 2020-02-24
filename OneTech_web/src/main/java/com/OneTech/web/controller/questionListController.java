package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.QuestionListService;
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
        try{
            questionListService.questionPublish(getRequestJson());
            statusBean.setRespMsg("发布成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("发布失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("getMyQuestion")
    public StatusBean<?> getMyQuestion(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getMyQuestion(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getAllQuestionListByLanguageOption")
    public StatusBean<?> getAllQuestionListByLanguageOption(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getAllQuestionListByLanguageOption(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getAllQuestionList")
    public StatusBean<?> getAllQuestionList(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getAllQuestionList());
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getQuestionInformationById")
    public StatusBean<?> getQuestionInformationById(){
        StatusBean<QuestionListVO> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getQuestionInformationById(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getMyFollow")
    public StatusBean<?> getMyFollow(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getMyFollow(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("getMyAnswer")
    public StatusBean<?> getMyAnswer(){
        StatusBean<List<QuestionListVO>> statusBean = new StatusBean();
        try{
            statusBean.setData(questionListService.getMyAnswer(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("查询失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

}
