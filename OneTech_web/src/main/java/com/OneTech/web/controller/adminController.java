package com.OneTech.web.controller;


import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.controller.CommonController;
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
                statusBean.setToken("123");
                statusBean.setRespMsg("登陆成功");
            }
            else{
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg("登陆失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg("登陆失败");
        }
        return statusBean;
    }
}
