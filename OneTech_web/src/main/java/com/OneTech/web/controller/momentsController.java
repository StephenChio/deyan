package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.MomentsConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.CommentsService;
import com.OneTech.service.service.MomentsService;
import com.OneTech.service.service.ResourceService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("moments")
public class momentsController extends CommonController {
    @Autowired
    MomentsService momentsService;
    @Autowired
    AddressListService addressListService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;
    @Autowired
    CommentsService commentsService;

    @PostMapping("publish")
    public StatusBean<?> publish() {
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            momentsService.publish(jsonObject);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.PUBLISH_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.PUBLISH_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("getMoments")
    public StatusBean<?> getMoments() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(addressListService.getMomentsFriendList(jsonObject));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getMomentById")
    public StatusBean<?> getMomentById() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(addressListService.getMomentById(jsonObject));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("getMomentsByWechatId")
    public StatusBean<?> getMomentsByWechatId() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(addressListService.getMomentsByWechatId(jsonObject));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("getMomentsPictureByWechatId")
    public StatusBean<?> getMomentsPictureByWechatId() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(addressListService.getMomentsPictureByWechatId(jsonObject));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.QUERY_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
    @PostMapping("deleteMomentsPicture")
    public StatusBean<?> deleteMomentsPicture(){
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            momentsService.deleteMomentsPicture(jsonObject);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.DELETE_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("deleteMomentsById")
    public StatusBean<?> deleteMomentsById(){
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            momentsService.deleteMomentsById(jsonObject);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(MomentsConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(MomentsConstants.DELETE_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
}
