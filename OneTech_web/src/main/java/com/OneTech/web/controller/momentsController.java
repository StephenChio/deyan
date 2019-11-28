package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.MomentsService;
import com.OneTech.service.service.ResourceService;
import com.OneTech.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
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
    @PostMapping("publish")
    public StatusBean<?> publish(){
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            momentsService.publish(getRequestJson());
            List<UserInfoBean> userInfoBeans = addressListService.getFriendList(getRequestJson());
            for(UserInfoBean userInfoBean : userInfoBeans){
                String user = "tab3"+userInfoBean.getWechatId();
                TextMessage textMessage = new TextMessage("朋友圈消息");
                springWebSocketHandler.sendMessageToUser(user,textMessage);
            }
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("发表成功");
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("发表异常!"+e);
        }
        return statusBean;
    }

    @PostMapping("getMoments")
    public StatusBean<?> getMoments(){
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        try {
            List<MomentsVO> momentsVOs = addressListService.getMomentsFriendList(getRequestJson());
            for(MomentsVO mV :momentsVOs){
                if(mV.getPictureId()!=null){
                    List<String> pictureImgPath = new ArrayList<>();
                    pictureImgPath = resourceService.getPictureImgPath(mV.getPictureId());
                    mV.setPictureImgPath(pictureImgPath);
                }
            }
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("查询成功");
            statusBean.setData(momentsVOs);
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("查询异常!"+e);
        }
        return statusBean;
    }
}
