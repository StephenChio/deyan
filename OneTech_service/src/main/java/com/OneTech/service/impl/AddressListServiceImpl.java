package com.OneTech.service.impl;

import com.OneTech.common.constants.AddressListAccpetStatus;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.MomentsVO;
import com.OneTech.model.mapper.AddressListMapper;
import com.OneTech.model.mapper.UserInfoMapper;
import com.OneTech.model.model.AddressListBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.UserInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.OneTech.common.vo.NewFriendVO;

import java.util.Date;
import java.util.List;

@Service("AddressListServiceImpl")
public class AddressListServiceImpl extends BaseServiceImpl<AddressListBean> implements AddressListService {
    @Autowired
    AddressListMapper addressListMapper;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public boolean sendVerification(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("wechatId"));
        addressListBean.setFWechatId(requestJson.getString("fWechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.WAIT);
        if(this.selectOne(addressListBean)!=null){
            return false;
        }
        addressListBean.setId(UUIDUtils.getRandom32());
        addressListBean.setVerificationMsg(requestJson.getString("verificationMsg"));
        addressListBean.setCreateTime(new Date());
        this.save(addressListBean);
        return true;
    }

    @Override
    public List<NewFriendVO> getNewFriend(JSONObject requestJson) throws Exception {
        return userInfoMapper.getNewFriend(requestJson);
    }

    @Override
    public void addConfirm(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("fWechatId"));
        addressListBean.setFWechatId(requestJson.getString("wechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.WAIT);
        addressListBean = this.selectOne(addressListBean);
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        addressListBean.setUpdateTime(new Date());
        this.saveOrUpdate(addressListBean);
    }

    @Override
    public List<UserInfoBean> getFriendList(JSONObject requestJson) throws Exception {
        List<UserInfoBean> returnUserInfoBeans = userInfoMapper.getFriendList(requestJson);
        return returnUserInfoBeans;
    }

    @Override
    public void deleteFriend(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("fWechatId"));
        addressListBean.setFWechatId(requestJson.getString("wechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        AddressListBean addressList;
        addressList = this.selectOne(addressListBean);
        if(addressList==null){
            addressListBean.setWechatId(requestJson.getString("wechatId"));
            addressListBean.setFWechatId(requestJson.getString("fWechatId"));
            addressList = this.selectOne(addressListBean);
        }
        addressList.setUpdateTime(new Date());
        addressList.setAccpetStatus(AddressListAccpetStatus.DELETED);
        this.saveOrUpdate(addressList);
    }

    @Override
    public List<MomentsVO> getMomentsFriendList(JSONObject requestJson) throws Exception {
        return userInfoMapper.getMomentsFriendList(requestJson);
    }
}
