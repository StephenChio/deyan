package com.OneTech.web.controller;


import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.AnswerLikeListConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.AnswerLikeListService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answerLikeList")
public class answerLikeListController extends CommonController {
    @Autowired
    AnswerLikeListService answerLikeListService;

    @PostMapping("clickLike")
    public StatusBean<?> clickLike() {
        StatusBean<AnswerListVO> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try {
            answerLikeListService.clickLike(jsonObject);
            statusBean.setRespMsg(AnswerLikeListConstants.LIKE_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(AnswerLikeListConstants.LIKE_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("clickDisLike")
    public StatusBean<?> clickDisLike() {
        StatusBean<AnswerListVO> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try {
            answerLikeListService.clickDisLike(jsonObject);
            statusBean.setRespMsg(AnswerLikeListConstants.CANCEL_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(AnswerLikeListConstants.CANCEL_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }

    @PostMapping("judgeIsLike")
    public StatusBean<?> judgeIsLike() {
        StatusBean<Boolean> statusBean = new StatusBean();
        JSONObject jsonObject = getRequestJson();
        try {
            statusBean.setData(answerLikeListService.judgeIsLike(jsonObject));
            statusBean.setRespMsg(AnswerLikeListConstants.QUERY_SUCCESS);
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg(AnswerLikeListConstants.QUERY_FAIL + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(jsonObject));
        return statusBean;
    }
}
