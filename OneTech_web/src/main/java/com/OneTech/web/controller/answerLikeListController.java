package com.OneTech.web.controller;


import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.AnswerListVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.AnswerLikeListService;
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
        try {
            answerLikeListService.clickLike(getRequestJson());
            statusBean.setRespMsg("点赞成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("点赞失败" + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("clickDisLike")
    public StatusBean<?> clickDisLike() {
        StatusBean<AnswerListVO> statusBean = new StatusBean();
        try {
            answerLikeListService.clickDisLike(getRequestJson());
            statusBean.setRespMsg("取消成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("取消失败" + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }

    @PostMapping("judgeIsLike")
    public StatusBean<?> judgeIsLike() {
        StatusBean<Boolean> statusBean = new StatusBean();
        try {
            statusBean.setData(answerLikeListService.judgeIsLike(getRequestJson()));
            statusBean.setRespMsg("查询成功");
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespMsg("查询失败" + e);
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
