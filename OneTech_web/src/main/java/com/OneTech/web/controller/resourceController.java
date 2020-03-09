package com.OneTech.web.controller;


import com.OneTech.common.constants.controllerConstants.ResourceConstants;
import com.OneTech.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.OneTech.service.service.ResourceService;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.vo.StatusBean;

import java.util.List;

@RestController
@RequestMapping("resource")
public class resourceController extends CommonController {
    @Autowired
    ResourceService resourceService;

    @PostMapping("get4MomentsImgByWechatId")
    public StatusBean<?> get4MomentsImgByWechatId() {
        StatusBean<List<String>> statusBean = new StatusBean<>();
        try {
            statusBean.setData(resourceService.get4MomentsImgByWechatId(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(ResourceConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(ResourceConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

}
