package com.OneTech.web.controller;


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
            statusBean.setRespMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("查询异常!" + e);
        }
        return statusBean;
    }

}
