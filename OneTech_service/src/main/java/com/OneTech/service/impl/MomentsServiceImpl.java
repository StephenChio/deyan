package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.util.UploadUtils;
import com.OneTech.model.model.MomentsBean;
import com.OneTech.model.model.ResourceBean;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.service.service.MomentsService;
import com.OneTech.service.service.ResourceService;
import com.OneTech.service.service.UserInfoService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("MomentsServiceImpl")
public class MomentsServiceImpl extends BaseServiceImpl<MomentsBean> implements MomentsService {
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    ResourceService resourceService;
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
        if(BooleanUtils.isNotEmpty(requestJson.getString("pictureMoments"))){
            String pictureMoments[] = requestJson.getString("pictureMoments").split(",");
            String pictureId = UUIDUtils.getRandom32();
            momentsBean.setpictureId(pictureId);
            List<ResourceBean> resourceBeans = new ArrayList<>();
            /**
             * 创建文件夹
             */
            java.net.URL url = this.getClass().getResource("/");
            String backage = "img/"+String.valueOf(requestJson.getString("wechatId").hashCode());
            //微信号hash值作为照片名字
            String path = url + backage;
            if (path.startsWith("file:")) {
                path = path.substring(5, path.length());
            }
            File df;
            if ((url +backage+"/").startsWith("file:")) {
                df = new File((url +backage+"/").substring(5, (url +backage+"/").length()));
            } else {
                df = new File(url +backage+"/");
            }
            if (!df.exists()) df.mkdir();
             /**
             * 创建文件夹
             */
            if (!df.exists()) df.mkdir();
            for(String picture : pictureMoments){
                ResourceBean resourceBean = new ResourceBean();
                String savaPath = backage+"/"+UUIDUtils.getRandom32()+".png";
                /**
                 * 上传图片
                 */
                UploadUtils.generateImage(picture,(url+savaPath).substring(5, (url+savaPath).length()));
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

    }

}
