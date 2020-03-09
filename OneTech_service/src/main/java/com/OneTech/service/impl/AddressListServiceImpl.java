package com.OneTech.service.impl;

import com.OneTech.common.vo.FriendListVO;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import com.OneTech.model.model.MomentsBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.OneTech.common.constants.AddressListAccpetStatus;
import com.OneTech.common.util.pingyinUtils.CharacterUtil;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.CommentsService;
import com.OneTech.service.service.ResourceService;
import com.OneTech.service.service.UserInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.TextMessage;
import com.OneTech.model.mapper.AddressListMapper;
import com.OneTech.model.mapper.UserInfoMapper;
import com.OneTech.model.model.AddressListBean;
import org.springframework.stereotype.Service;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.vo.NewFriendVO;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.MomentsVO;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
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
    @Autowired
    ResourceService resourceService;
    @Autowired
    CommentsService commentsService;
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;

    /**
     * 发送验证信息
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public boolean sendVerification(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("wechatId"));
        addressListBean.setFWechatId(requestJson.getString("fWechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.WAIT);
        if (this.selectOne(addressListBean) != null) return false;
        addressListBean.setId(UUIDUtils.getRandom32());
        addressListBean.setVerificationMsg(requestJson.getString("verificationMsg"));
        addressListBean.setCreateTime(new Date());
        this.save(addressListBean);
        /**
         * 发送添加好友信息
         */
        String user = "tab2" + "and" + requestJson.getString("fWechatId");
        TextMessage textMessage = new TextMessage("新的好友申请");
        springWebSocketHandler.sendMessageToUser(user, textMessage, true);
        return true;
    }

    /**
     * 获得新好友列表
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<NewFriendVO> getNewFriend(JSONObject requestJson) throws Exception {
        return userInfoMapper.getNewFriend(requestJson);
    }

    /**
     * 确认添加好友
     *
     * @param requestJson
     * @throws Exception
     */
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

    /**
     * 获得好友列表
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<FriendListVO> getFriendList(JSONObject requestJson) throws Exception {
        return userInfoMapper.getFriendList(requestJson);
    }

    /**
     * 获得根据配音首字母排序的好友列表
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getFriendListByPY(JSONObject requestJson) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<FriendListVO> friendLists = userInfoMapper.getFriendList(requestJson);
        for (FriendListVO friendList : friendLists) {
            JSONArray jsonArray = new JSONArray();
            String firstLetter;
            if (!BooleanUtils.isEmpty(friendList.getRemarkName())) {
                firstLetter = CharacterUtil.convertHanzi2Pinyin(friendList.getRemarkName().substring(0, 1), false);
            } else {
                firstLetter = CharacterUtil.convertHanzi2Pinyin(friendList.getUserName().substring(0, 1), false);
            }
            if (!firstLetter.matches("^[A-Za-z]+$")) {//不是字母
                firstLetter = "#";
            } else {//是字母
                firstLetter = firstLetter.toUpperCase();//转大写
            }
            if (BooleanUtils.isNotEmpty(jsonObject.get(firstLetter))) {
                jsonArray = jsonObject.getJSONArray(firstLetter);
            }
            jsonArray.add(friendList);
            jsonObject.put(firstLetter, jsonArray);
        }
        return jsonObject;
    }

    /**
     * 删除好友
     *
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void deleteFriend(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("fWechatId"));
        addressListBean.setFWechatId(requestJson.getString("wechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        AddressListBean addressList;
        addressList = this.selectOne(addressListBean);
        if (addressList == null) {
            addressListBean.setWechatId(requestJson.getString("wechatId"));
            addressListBean.setFWechatId(requestJson.getString("fWechatId"));
            addressList = this.selectOne(addressListBean);
        }
        addressList.setUpdateTime(new Date());
        addressList.setAccpetStatus(AddressListAccpetStatus.DELETED);
        this.saveOrUpdate(addressList);
    }

    /**
     * 获得朋友圈朋友内容列表
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<MomentsVO> getMomentsFriendList(JSONObject requestJson) throws Exception {
        List<MomentsVO> momentsVOs = userInfoMapper.getMomentsFriendList(requestJson);
        for (MomentsVO mV : momentsVOs) {
            if (mV.getPictureId() != null) {
                List<String> pictureImgPath = new ArrayList<>();
                pictureImgPath = resourceService.getPictureImgPath(mV.getPictureId());
                mV.setPictureImgPath(pictureImgPath);
            }
            /**
             *  获取点赞内容
             */
            JSONObject jsonObject = requestJson;
            jsonObject.put("momentId", mV.getId());
            List<FriendListVO> LikeList = commentsService.selectLike(jsonObject);
            if (BooleanUtils.isNotEmpty(LikeList)) {
                mV.setLikeName(LikeList);
            }
//                List<CommentsBean> CommentsList = commentsService.selectComments(jsonObject);
            /**
             * 获取评论内容
             */
            //TODO

        }
        return momentsVOs;
    }

    @Override
    public List<MomentsVO> getMomentById(JSONObject requestJson) throws Exception {
        List<MomentsVO> momentsVOs = userInfoMapper.getMomentById(requestJson);
        for (MomentsVO mV : momentsVOs) {
            if (mV.getPictureId() != null) {
                List<String> pictureImgPath = new ArrayList<>();
                pictureImgPath = resourceService.getPictureImgPath(mV.getPictureId());
                mV.setPictureImgPath(pictureImgPath);
            }
            /**
             *  获取点赞内容
             */
            JSONObject jsonObject = requestJson;
            jsonObject.put("momentId", mV.getId());
            List<FriendListVO> LikeList = commentsService.selectLike(jsonObject);
            if (BooleanUtils.isNotEmpty(LikeList)) {
                mV.setLikeName(LikeList);
            }
//                List<CommentsBean> CommentsList = commentsService.selectComments(jsonObject);
            /**
             * 获取评论内容
             */
            //TODO

        }
        return momentsVOs;
    }

    /**
     * 根据微信号获取该用户朋友圈内容
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<MomentsVO> getMomentsByWechatId(JSONObject requestJson) throws Exception {
        List<MomentsVO> momentsVOs = userInfoMapper.getMomentsByWechatId(requestJson);
        for (MomentsVO mV : momentsVOs) {
            if (mV.getPictureId() != null) {
                List<String> pictureImgPath = new ArrayList<>();
                pictureImgPath = resourceService.getPictureImgPath(mV.getPictureId());
                mV.setPictureImgPath(pictureImgPath);
            }
        }
        return momentsVOs;
    }

    /**
     * 获取朋友圈相册
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public List<MomentsVO> getMomentsPictureByWechatId(JSONObject requestJson) throws Exception {
        List<MomentsVO> momentsVOs = userInfoMapper.getMomentsPictureByWechatId(requestJson);
        for (MomentsVO mV : momentsVOs) {
            if (mV.getPictureId() != null) {
                List<String> pictureImgPath = new ArrayList<>();
                pictureImgPath = resourceService.getPictureImgPath(mV.getPictureId());
                mV.setPictureImgPath(pictureImgPath);
            }
        }
        return momentsVOs;
    }

    @Override
    public boolean isFriend(JSONObject requestJson) throws Exception {
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setWechatId(requestJson.getString("fWechatId"));
        addressListBean.setFWechatId(requestJson.getString("wechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        if(BooleanUtils.isNotEmpty(this.selectOne(addressListBean))){
            return true;
        }
        addressListBean = new AddressListBean();
        addressListBean.setFWechatId(requestJson.getString("fWechatId"));
        addressListBean.setWechatId(requestJson.getString("wechatId"));
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        if(BooleanUtils.isNotEmpty(this.selectOne(addressListBean))){
            return true;
        }
        return false;
    }
}
