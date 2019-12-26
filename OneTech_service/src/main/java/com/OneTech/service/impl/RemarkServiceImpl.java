package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.model.model.RemarkBean;
import com.OneTech.service.service.RemarkService;
import com.OneTech.service.service.UserInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("RemarkServiceImpl")
public class RemarkServiceImpl extends BaseServiceImpl<RemarkBean> implements RemarkService {
    @Autowired
    UserInfoService userInfoService;
    /**
     * 更新备注
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void updateRemakers(JSONObject requestJson) throws Exception {
        RemarkBean remarkBean = new RemarkBean();
        remarkBean.setWechatId(requestJson.getString("wechatId"));
        remarkBean.setFWechatId(requestJson.getString("fWechatId"));
        remarkBean = this.selectOne(remarkBean);
        if(remarkBean==null){
            remarkBean = new RemarkBean();
            remarkBean.setId(UUIDUtils.getRandom32());
            remarkBean.setWechatId(requestJson.getString("wechatId"));
            remarkBean.setFWechatId(requestJson.getString("fWechatId"));
            if(BooleanUtils.isEmpty(requestJson.getString("remarkName"))){
                remarkBean.setRemarkName(userInfoService.getUserNameByWechatId(requestJson.getString("fWechatId")));
            }else {
                remarkBean.setRemarkName(requestJson.getString("remarkName"));
            }
            remarkBean.setPhone(requestJson.getString("phone"));
            remarkBean.setDescribeText(requestJson.getString("describeText"));
            remarkBean.setCreateTime(new Date());

            this.save(remarkBean);

        }else {
            if(BooleanUtils.isEmpty(requestJson.getString("remarkName"))){
                remarkBean.setRemarkName(userInfoService.getUserNameByWechatId(requestJson.getString("fWechatId")));
            }else {
                remarkBean.setRemarkName(requestJson.getString("remarkName"));
            }
            remarkBean.setPhone(requestJson.getString("phone"));
            remarkBean.setDescribeText(requestJson.getString("describeText"));
            remarkBean.setUpdateTime(new Date());

            this.saveOrUpdate(remarkBean);
        }
    }

    /**
     * 更新标签
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void updateTag(JSONObject requestJson) throws Exception {

    }
}
