package com.OneTech.web.controller;


import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.controllerConstants.AdminConstants;
import com.OneTech.common.controller.CommonController;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class adminController extends CommonController {
    @Autowired
    AdminService adminService;
    @PostMapping("login")
    public StatusBean<?> login(){
        StatusBean<?> statusBean = new StatusBean<>();
        try {
            if (adminService.login(getRequestJson())) {
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(AdminConstants.LOGIN_SUCCESS);
            }
            else{
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(AdminConstants.LOGIN_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(AdminConstants.LOGIN_FAIL);
        }
        statusBean.setToken(JwtTokenUtil.updateToken(getRequestJson()));
        return statusBean;
    }
}
