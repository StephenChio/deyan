package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.model.model.AdminBean;
import com.OneTech.service.service.AdminService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


@Service("AdminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<AdminBean> implements AdminService {
    @Override
    public boolean login(JSONObject requestJson) throws Exception {
        AdminBean adminBean = new AdminBean();
        adminBean.setAdminName(requestJson.getString("username"));
        adminBean.setAdminPassword(requestJson.getString("password"));
        adminBean = this.selectOne(adminBean);
        if(adminBean!=null){
            return true;
        }
        else {
            return false;
        }
    }
}
