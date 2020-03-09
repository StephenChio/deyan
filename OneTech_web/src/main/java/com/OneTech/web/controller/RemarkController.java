package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.RemarkConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.RemarkBean;
import com.OneTech.service.service.RemarkService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("remark")
public class RemarkController extends CommonController {
    @Autowired
    RemarkService remarkService;

    @PostMapping("updateRemakers")
    public StatusBean<?> updateRemakers(){
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try{
            remarkService.updateRemakers(jsonObject);
            statusBean.setRespMsg(RemarkConstants.SETTING_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(RemarkConstants.SETTING_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("updateTag")
    public StatusBean<?> updateTag(){
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try{
            remarkService.updateTag(jsonObject);
            statusBean.setRespMsg(RemarkConstants.SETTING_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(RemarkConstants.SETTING_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getRemakers")
    public StatusBean<?> getRemakers(){
        StatusBean<RemarkBean> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(remarkService.getRemakers(jsonObject));
            statusBean.setRespMsg(RemarkConstants.SETTING_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(RemarkConstants.SETTING_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

}
