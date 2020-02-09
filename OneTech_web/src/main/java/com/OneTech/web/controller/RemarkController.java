package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.RemarkBean;
import com.OneTech.service.service.RemarkService;
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
        try{
            remarkService.updateRemakers(getRequestJson());
            statusBean.setRespMsg("设置成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("设置失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("updateTag")
    public StatusBean<?> updateTag(){
        StatusBean<?> statusBean = new StatusBean<>();
        try{
            remarkService.updateTag(getRequestJson());
            statusBean.setRespMsg("设置成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("设置失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
    @PostMapping("getRemakers")
    public StatusBean<?> getRemakers(){
        StatusBean<RemarkBean> statusBean = new StatusBean<>();
        try{
            statusBean.setData(remarkService.getRemakers(getRequestJson()));
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
