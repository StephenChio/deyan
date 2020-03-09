package com.OneTech.web.controller;

import com.OneTech.common.constants.controllerConstants.AddressListConstants;
import com.OneTech.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.OneTech.common.controller.CommonController;
import com.OneTech.service.service.AddressListService;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.common.vo.NewFriendVO;
import com.OneTech.common.vo.StatusBean;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

@RestController
@RequestMapping("addressList")
public class addressListController extends CommonController {
    @Autowired
    AddressListService addressListService;
    @Autowired
    UserInfoService userInfoService;

    @PostMapping(value = "sendVerification")
    public StatusBean<?> sendVerification() {
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            if (!addressListService.sendVerification(jsonObject)) {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(AddressListConstants.SEND_AGAIN_MSG);
                return statusBean;
            }
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.SEND_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.SEND_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping(value = "getNewFriend")
    public StatusBean<?> getNewFriend() {
        StatusBean<List<NewFriendVO>> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.QUERY_SUCCESS);
            statusBean.setData(addressListService.getNewFriend(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.QUERY_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping(value = "addConfirm")
    public StatusBean<?> addConfirm() {
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            addressListService.addConfirm(jsonObject);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.ADD_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping(value = "getFriendList")
    public StatusBean<?> getFriendList() {
        StatusBean<JSONObject> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.QUERY_SUCCESS);
            statusBean.setData(addressListService.getFriendListByPY(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.QUERY_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("deleteFriend")
    public StatusBean<?> deleteFriend() {
        StatusBean<?> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            addressListService.deleteFriend(jsonObject);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.DELETE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }


    @PostMapping("isFriend")
    public StatusBean<?> isFriend() {
        StatusBean<Boolean> statusBean = new StatusBean<>();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(addressListService.isFriend(jsonObject));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(AddressListConstants.QUERY_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AddressListConstants.QUERY_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
}
