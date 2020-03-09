package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.CodeUtils.SaltUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.model.IdSaltBean;
import com.OneTech.service.service.IdSaltService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


@Service("IdSaltServiceImpl")
public class IdSaltServiceImpl extends BaseServiceImpl<IdSaltBean> implements IdSaltService {
    @Override
    public String getSalt(JSONObject requestJson) throws Exception {
        IdSaltBean idSaltBean = new IdSaltBean();
        idSaltBean.setPhone(requestJson.getString("phone"));
        idSaltBean = this.selectOne(idSaltBean);
        if(BooleanUtils.isNotEmpty(idSaltBean)){
            return idSaltBean.getSalt();
        }
        else{
            idSaltBean = new IdSaltBean();
            idSaltBean.setId(UUIDUtils.getRandom32());
            idSaltBean.setPhone(requestJson.getString("phone"));
            String salt = SaltUtils.getSalt(requestJson.getString("phone"));
            idSaltBean.setSalt(salt);
            this.save(idSaltBean);
            return salt;
        }
    }
}
