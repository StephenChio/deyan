package com.OneTech.service.service;

import com.OneTech.common.service.IBaseService;
import com.OneTech.model.model.AdminBean;
import com.alibaba.fastjson.JSONObject;

public interface AdminService extends IBaseService<AdminBean> {
    boolean login(JSONObject requestJson)throws Exception;
}
