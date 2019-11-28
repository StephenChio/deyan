package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import com.OneTech.common.vo.NewFriendVO;
import java.util.List;

@RestController
@RequestMapping("addressList")
public class addressListController extends CommonController {
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;
    @Autowired
    AddressListService addressListService;
    @Autowired
    UserInfoService userInfoService;
    @PostMapping(value = "sendVerification")
    public StatusBean<?> sendVerification(){
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            if(!addressListService.sendVerification(getRequestJson())){
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("请勿重复发送验证消息");
                return statusBean;
            }
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("发送成功");
            String user = "tab2"+getRequestJson().getString("fWechatId");
            TextMessage textMessage = new TextMessage("新的好友申请");
            springWebSocketHandler.sendMessageToUser(user,textMessage);
        }
        catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("发送失败");
        }
        return statusBean;
    }
    @PostMapping(value = "getNewFriend")
    public StatusBean<?> getNewFriend(){
        StatusBean<List<NewFriendVO>> statusBean = new StatusBean<>();
        try {
            List<NewFriendVO> newFriendVOBeans =  addressListService.getNewFriend(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("查询成功");
            statusBean.setData(newFriendVOBeans);
        }
        catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("查询失败");
        }
        return statusBean;
    }
    @PostMapping(value = "addConfirm")
    public StatusBean<?> addConfirm(){
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            addressListService.addConfirm(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("添加成功");
        }
        catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("添加失败");
        }
        return statusBean;
    }
    @PostMapping(value = "getFriendList")
    public StatusBean<?> getFriendList(){
        StatusBean<List<UserInfoBean>> statusBean = new StatusBean<>();
        try {
            List<UserInfoBean> userInfoBeans = addressListService.getFriendList(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("添加成功");
            statusBean.setData(userInfoBeans);
        }
        catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("添加失败");
        }
        return statusBean;
    }
    @PostMapping("deleteFriend")
    public StatusBean<?> deleteFriend(){
        StatusBean<List<UserInfoBean>> statusBean = new StatusBean<>();
        try{
            addressListService.deleteFriend(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("删除失败");
        }
        return statusBean;
    }
}
