package com.OneTech.service.impl;

import com.OneTech.common.vo.FriendListVO;
import com.OneTech.device.websocket.handler.SpringWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.ResourceService;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.service.service.MomentsService;
import org.springframework.web.socket.TextMessage;
import org.springframework.stereotype.Service;
import com.OneTech.model.model.ResourceBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UploadUtils;
import com.OneTech.model.model.MomentsBean;
import com.OneTech.common.util.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

@Service("MomentsServiceImpl")
public class MomentsServiceImpl extends BaseServiceImpl<MomentsBean> implements MomentsService {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    AddressListService addressListService;
    @Autowired
    SpringWebSocketHandler springWebSocketHandler;
    @Value("${localUrl}")
    public String url;

    /**
     * 发布朋友圈
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void publish(JSONObject requestJson) throws Exception {
        MomentsBean momentsBean = new MomentsBean();
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = userInfoService.selectOne(userInfoBean);
        momentsBean.setId(UUIDUtils.getRandom32());
        momentsBean.setMomentsId(userInfoBean.getMomentsId());
        momentsBean.setText(requestJson.getString("text"));
        momentsBean.setCreateTime(new Date());
        if (BooleanUtils.isNotEmpty(requestJson.getString("pictureMoments"))) {
            String pictureMoments[] = requestJson.getString("pictureMoments").split(",");
            String pictureId = UUIDUtils.getRandom32();
            momentsBean.setPictureId(pictureId);
            List<ResourceBean> resourceBeans = new ArrayList<>();
            /**
             * 创建文件夹
             */
            String backage = "img/" + String.valueOf(requestJson.getString("wechatId").hashCode());
            //微信号hash值作为照片名字
            File df;
            df = new File(url + backage + "/");
            if (!df.exists()) df.mkdir();
            /**
             * 创建文件夹
             */
            if (!df.exists()) df.mkdir();
            for (String picture : pictureMoments) {
                ResourceBean resourceBean = new ResourceBean();
                String savaPath = backage + "/" + UUIDUtils.getRandom32() + ".png";
                /**
                 * 上传图片
                 */
                UploadUtils.generateImage(picture, url + savaPath);
                /**
                 * 上传图片
                 */
                resourceBean.setId(UUIDUtils.getRandom32());
                resourceBean.setPictureId(pictureId);
                resourceBean.setImgPath(savaPath);
                resourceBean.setCreateTime(new Date());
                resourceBeans.add(resourceBean);
            }
            resourceService.batchInsert(resourceBeans);
        }
        this.save(momentsBean);
        /**
         * 群发朋友圈发布消息
         */
        List<FriendListVO> friendList = addressListService.getFriendList(requestJson);
        for (FriendListVO userInfo : friendList) {
            String user = "tab3" +"and"+ userInfo.getWechatId();
            TextMessage textMessage = new TextMessage("朋友圈消息");
            springWebSocketHandler.sendMessageToUser(user, textMessage, true);
        }
    }
    /**
     * 删除朋友圈相册
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void deleteMomentsPicture(JSONObject requestJson) throws Exception {
        MomentsBean momentsBean = new MomentsBean();
        momentsBean.setPictureId(requestJson.getString("pictureId"));
        List<MomentsBean> momentsBeans = this.select(momentsBean);
        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setPictureId(requestJson.getString("pictureId"));
        List<ResourceBean> resourceBeans = resourceService.select(resourceBean);
        this.batchDelete(momentsBeans);
        resourceService.batchDelete(resourceBeans);
        for(ResourceBean resource:resourceBeans){
            String imagePath = resource.getImgPath();
            if (!BooleanUtils.isEmpty(imagePath) && imagePath.startsWith("img")) {
                File f = new File(url + imagePath);
                if (f.exists()) f.delete();
            }
        }
    }
}
