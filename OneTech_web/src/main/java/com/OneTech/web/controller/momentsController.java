package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.CommentsService;
import com.OneTech.service.service.MomentsService;
import com.OneTech.service.service.ResourceService;
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
        try {
            momentsService.publish(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("发表成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("发表异常!" + e);
        }
        return statusBean;
    }

    @PostMapping("getMoments")
    public StatusBean<?> getMoments() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        try {
            statusBean.setData(addressListService.getMomentsFriendList(getRequestJson()));
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("查询异常!" + e);
        }
        return statusBean;
    }

    @PostMapping("getMomentsByWechatId")
    public StatusBean<?> getMomentsByWechatId() {
        StatusBean<List<MomentsVO>> statusBean = new StatusBean<>();
        try {
            statusBean.setData(addressListService.getMomentsByWechatId(getRequestJson()));
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
