package com.OneTech.web.controller;

import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.QuestionListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.CollectAnswerListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            statusBean.setRespMsg("收藏成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("收藏失败" + e);
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
            statusBean.setRespMsg("取消收藏成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("取消收藏失败" + e);
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
            statusBean.setRespMsg("取消收藏成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("取消收藏失败" + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
