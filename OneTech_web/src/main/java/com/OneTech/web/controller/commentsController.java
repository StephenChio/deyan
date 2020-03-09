package com.OneTech.web.controller;

import com.OneTech.common.constants.controllerConstants.CommentsConstants;
import com.OneTech.common.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.service.service.CommentsService;
import com.OneTech.common.vo.StatusBean;

@RestController
@RequestMapping("comments")
public class commentsController extends CommonController {
    @Autowired
    CommentsService commentsService;

    @PostMapping("clickLike")
    public StatusBean<?> clickLike() {
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            commentsService.clickLike(getRequestJson());
            statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
            statusBean.setRespMsg(CommentsConstants.LIKE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(CommentsConstants.LIKE_FAIL + e);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
