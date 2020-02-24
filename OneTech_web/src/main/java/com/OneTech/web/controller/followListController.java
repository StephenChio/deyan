package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.FollowListService;
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
        try{
            statusBean.setData(followListService.isFollowed(getRequestJson()));
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

    @PostMapping("followQuestion")
    public StatusBean<?> followQuestion(){
        StatusBean<?> statusBean = new StatusBean();
        try{
            followListService.followQuestion(getRequestJson());
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

    @PostMapping("disFollowQuestion")
    public StatusBean<?> disFollowQuestion(){
        StatusBean<?> statusBean = new StatusBean();
        try{
            followListService.disFollowQuestion(getRequestJson());
            statusBean.setRespMsg("取消成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespMsg("取消失败"+e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}