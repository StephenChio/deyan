package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.CollectAnswerListConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.CollectAnswerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("collectAnswerList")
public class collectAnswerListController extends CommonController {
    @Autowired
    CollectAnswerListService collectAnswerListService;

    @PostMapping("collectAnswer")
    public StatusBean<?> collectAnswer() {
        StatusBean<Boolean> statusBean = new StatusBean();
        try {
            collectAnswerListService.collectAnswer(getRequestJson());
            statusBean.setRespMsg(CollectAnswerListConstants.COLLECT_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(CollectAnswerListConstants.COLLECT_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("disCollectAnswer")
    public StatusBean<?> disCollectAnswer() {
        StatusBean<Boolean> statusBean = new StatusBean();
        try {
            collectAnswerListService.disCollectAnswer(getRequestJson());
            statusBean.setRespMsg(CollectAnswerListConstants.CANCEL_COLLECT_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(CollectAnswerListConstants.CANCEL_COLLECT_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("judgeIsCollect")
    public StatusBean<?> judgeIsCollect() {
        StatusBean<Boolean> statusBean = new StatusBean();
        try {
            statusBean.setData(collectAnswerListService.judgeIsCollect(getRequestJson()));
            statusBean.setRespMsg(CollectAnswerListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(CollectAnswerListConstants.QUERY_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
