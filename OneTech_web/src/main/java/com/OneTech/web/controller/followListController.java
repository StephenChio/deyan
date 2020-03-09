package com.OneTech.web.controller;

import com.OneTech.common.constants.FollowConstants;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.FollowListConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.FollowListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("followList")
public class followListController extends CommonController {
    @Autowired
    FollowListService followListService;
    @PostMapping("isFollowed")
    public StatusBean<?> isFollowed(){
        StatusBean<Boolean> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(followListService.isFollowed(jsonObject));
            statusBean.setRespMsg(FollowListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(FollowListConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("followQuestion")
    public StatusBean<?> followQuestion(){
        StatusBean<?> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            followListService.followQuestion(jsonObject);
            statusBean.setRespMsg(FollowListConstants.SETTING_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(FollowListConstants.SETTING_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("disFollowQuestion")
    public StatusBean<?> disFollowQuestion(){
        StatusBean<?> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            followListService.disFollowQuestion(jsonObject);
            statusBean.setRespMsg(FollowListConstants.CANCEL_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(FollowListConstants.CANCEL_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
}
