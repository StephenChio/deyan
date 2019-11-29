package com.OneTech.service.impl;

import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.util.UploadUtils;
import com.OneTech.model.mapper.UserInfoMapper;
import com.OneTech.model.model.UserInfoBean;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.OneTech.service.service.UserInfoService;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoBean> implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Value("${localUrl}")
    public String url;

    @Override
    public List<UserInfoBean> searchFriend(JSONObject requestJson) throws Exception {
        return userInfoMapper.searchFriend(requestJson);
    }

    @Override
    public UserInfoBean updateName(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        userInfoBean.setUserName(requestJson.getString("userName"));
        userInfoBean.setUpdateTime(new Date());
        this.saveOrUpdate(userInfoBean);
        return userInfoBean;
    }

    @Override
    public UserInfoBean updateBackgroundImg(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        try {
            String savePath = "img/" + UUIDUtils.getRandom32() + ".png";
            String path = url + savePath;
            File df;
            df = new File(url + "img/");
            if (!df.exists()) df.mkdir();
//            System.out.println(requestJson.getString("imgPath"));
            /**
             * //删除之前图片
             */
            String backgroundImg = userInfoBean.getBackgroundImg();
            if (!BooleanUtils.isEmpty(backgroundImg) && backgroundImg.startsWith("img")) {
                File f = new File(url + backgroundImg);
                if (f.exists()) f.delete();
            }
            /**
             * 上传图片
             */
            UploadUtils.generateImage(requestJson.getString("backgroundImg"), path);

            /**
             * 更新数据库
             */
            userInfoBean.setBackgroundImg(savePath);
            userInfoBean.setUpdateTime(new Date());
            this.saveOrUpdate(userInfoBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoBean;

    }

    @Override
    public UserInfoBean updatePicture(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        try {
            String savePath = "img/" + UUIDUtils.getRandom32() + ".png";
            //微信号hash值作为照片名字
            String path = url + savePath;
            File df;
            df = new File(url + "img/");
            if (!df.exists()) df.mkdir();
//            System.out.println(requestJson.getString("imgPath"));
            /**
             * //删除之前图片
             */
            String imagePath = userInfoBean.getImgPath();
            if (!BooleanUtils.isEmpty(imagePath) && imagePath.startsWith("img")) {
                File f = new File(url + imagePath);
                if (f.exists()) f.delete();
            }
            /**
             * 上传图片
             */
            UploadUtils.generateImage(requestJson.getString("imgPath"), path);

            /**
             * 更新数据库
             */
            userInfoBean.setImgPath(savePath);
            userInfoBean.setUpdateTime(new Date());
            this.saveOrUpdate(userInfoBean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfoBean;
    }

}
