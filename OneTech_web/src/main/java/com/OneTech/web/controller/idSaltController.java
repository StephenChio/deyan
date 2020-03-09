package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.IdSaltConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.IdSaltService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("idSalt")
public class idSaltController extends CommonController {
    @Autowired
    IdSaltService idSaltService;

    @PostMapping("getSalt")
    public StatusBean<?> getSalt(){
        StatusBean<String> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try{
            statusBean.setData(idSaltService.getSalt(jsonObject));
            statusBean.setRespMsg(IdSaltConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg(IdSaltConstants.QUERY_FAIL+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        return statusBean;
    }
}
