package com.OneTech.service.impl;

import com.OneTech.common.constants.AddressListAccpetStatus;
import com.OneTech.common.constants.SystemConstants;
import com.OneTech.common.constants.TitleConstants;
import com.OneTech.common.constants.controllerConstants.MainConstants;
import com.OneTech.common.constants.controllerConstants.UserInfoConstants;
import com.OneTech.common.util.JwtTokenUtil;
import com.OneTech.common.vo.LoginVO;
import com.OneTech.common.vo.StatusBean;
import com.OneTech.model.model.AddressListBean;
import com.OneTech.model.model.IdSaltBean;
import com.OneTech.service.service.AddressListService;
import com.OneTech.service.service.IdSaltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import com.OneTech.common.service.impl.BaseServiceImpl;
import com.OneTech.service.service.UserInfoService;
import com.OneTech.model.mapper.UserInfoMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import com.OneTech.model.model.UserInfoBean;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UploadUtils;
import com.OneTech.common.util.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.File;

@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfoBean> implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    AddressListService addressListService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Value("${localUrl}")
    public String url;

    @Autowired
    IdSaltService idSaltService;

    @Override
    public List<UserInfoBean> searchFriend(JSONObject requestJson) throws Exception {
        return userInfoMapper.searchFriend(requestJson);
    }

    /**
     * 修改用户名
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
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

    /**
     * 更新朋友圈背景图片
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
            if (!BooleanUtils.isEmpty(backgroundImg) && backgroundImg.startsWith("img") && !backgroundImg.endsWith("background.png")) {
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
            throw e;
        }
        return userInfoBean;

    }

    /**
     * 更换头像
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
             * 上传图片
             */
            if (UploadUtils.generateImage(requestJson.getString("imgPath"), path)) {

                /**
                 * //删除之前图片
                 */
                String imagePath = userInfoBean.getImgPath();
                if (!BooleanUtils.isEmpty(imagePath) && imagePath.startsWith("img") && !imagePath.endsWith("head.png")) {
                    File f = new File(url + imagePath);
                    if (f.exists()) f.delete();
                }
                /**
                 * 更新数据库
                 */
                userInfoBean.setImgPath(savePath);
                userInfoBean.setUpdateTime(new Date());
                this.saveOrUpdate(userInfoBean);
            } else {
                return userInfoBean;
            }
        } catch (Exception e) {
            throw e;
        }
        return userInfoBean;
    }

    /**
     * 更换手机号码
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePhoneNum(JSONObject requestJson) throws Exception {
        String phone = requestJson.getString("phone");
        if (BooleanUtils.isEmpty(redisTemplate.opsForValue().get(phone))) {
            return false;
        }
        if (redisTemplate.opsForValue().get(phone).toString().equals(requestJson.getString("verifiCode"))) {
            UserInfoBean userInfoBean = new UserInfoBean();
            userInfoBean.setWechatId(requestJson.getString("wechatId"));
            userInfoBean = this.selectOne(userInfoBean);
            String oldPhone = userInfoBean.getPhone();
            userInfoBean.setPhone(phone);
            userInfoBean.setUpdateTime(new Date());
            this.saveOrUpdate(userInfoBean);
            IdSaltBean idSaltBean = new IdSaltBean();
            idSaltBean.setPhone(oldPhone);
            idSaltBean = idSaltService.selectOne(idSaltBean);
            idSaltBean.setPhone(phone);
            idSaltService.saveOrUpdate(idSaltBean);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public boolean updatePassword(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        if (!"true".equals(requestJson.getString("hasPassword"))) {
            userInfoBean.setPassWord(requestJson.getString("newPwd"));
            userInfoBean.setUpdateTime(new Date());
            this.saveOrUpdate(userInfoBean);
            return true;
        }
        if (requestJson.getString("oldPwd").equals(userInfoBean.getPassWord())) {
            userInfoBean.setPassWord(requestJson.getString("newPwd"));
            userInfoBean.setUpdateTime(new Date());
            this.saveOrUpdate(userInfoBean);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 初始化新用户
     *
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public LoginVO initUser(String phone) throws Exception {
        LoginVO loginVO = new LoginVO();
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setId(UUIDUtils.getRandom32());
        userInfoBean.setUserName(phone);
        userInfoBean.setPhone(phone);
        String wechatId = UUIDUtils.getRandom32().substring(0, 8);
        userInfoBean.setWechatId(wechatId);
        userInfoBean.setMomentsId(UUIDUtils.getRandom32());
        userInfoBean.setImgPath(UserInfoConstants.INIT_IMG);
        userInfoBean.setBackgroundImg(UserInfoConstants.INIT_BACKGROUD);
        userInfoBean.setPoints(UserInfoConstants.INIT_POINTS);
        userInfoBean.setTitle(TitleConstants.LEVEL1);
        userInfoBean.setUserLevel(UserInfoConstants.INIT_LEVEL);
        userInfoBean.setExperience(UserInfoConstants.INIT_EXP);
        userInfoBean.setMoney(UserInfoConstants.INIT_MONEY);
        userInfoBean.setCreateTime(new Date());
        this.save(userInfoBean);
        /**
         * 构造登陆返回对象
         */
        loginVO.setWechatId(userInfoBean.getWechatId());
        loginVO.setBackgroundImg(userInfoBean.getBackgroundImg());
        loginVO.setImgPath(userInfoBean.getImgPath());
        loginVO.setPhone(userInfoBean.getPhone());
        loginVO.setUserName(userInfoBean.getUserName());
        loginVO.setPoints(userInfoBean.getPoints());
        loginVO.setTitle(userInfoBean.getTitle());
        loginVO.setUserLevel(userInfoBean.getUserLevel());
        loginVO.setExperience(userInfoBean.getExperience());
        loginVO.setMoney(userInfoBean.getMoney());
        loginVO.setSex(userInfoBean.getSex());
        loginVO.setPosition(userInfoBean.getPosition());
        loginVO.setSign(userInfoBean.getSign());
        loginVO.setHasPassword(false);
        /**
         * 默认添加机器人好友
         */
        addRobot(wechatId);
        return loginVO;
    }

    /**
     * 添加机器人好友
     * @param wechatId
     * @throws Exception
     */
    public void addRobot(String wechatId)throws Exception{
        AddressListBean addressListBean = new AddressListBean();
        addressListBean.setId(UUIDUtils.getRandom32());
        addressListBean.setWechatId(UserInfoConstants.ROBOT_ID);
        addressListBean.setFWechatId(wechatId);
        addressListBean.setAccpetStatus(AddressListAccpetStatus.ACCPETED);
        addressListBean.setCreateTime(new Date());
        addressListService.save(addressListBean);
    }

    @Override
    public String getUserNameByWechatId(String wechatId) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(wechatId);
        userInfoBean = this.selectOne(userInfoBean);
        return userInfoBean.getUserName();
    }

    /**
     * 密码登陆
     * @param requestJson
     * @return
     */
    public StatusBean<?> passwordLogin(JSONObject requestJson) throws Exception{
        StatusBean<LoginVO> statusBean = new StatusBean();
        UserInfoBean userInfo = new UserInfoBean();
        LoginVO loginVO = new LoginVO();
        try {
            String phone = requestJson.getString("phone");
            String password = requestJson.getString("password");
            userInfo.setPhone(phone);
            userInfo.setPassWord(password);
            userInfo = this.selectOne(userInfo);
            if (BooleanUtils.isEmpty(userInfo)) {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(UserInfoConstants.PASS_ERROR_ERROR);
            } else {
                /**
                 * 构造登陆返回对象
                 */
                loginVO.setWechatId(userInfo.getWechatId());
                loginVO.setBackgroundImg(userInfo.getBackgroundImg());
                loginVO.setImgPath(userInfo.getImgPath());
                loginVO.setPhone(userInfo.getPhone());
                loginVO.setUserName(userInfo.getUserName());

                loginVO.setPoints(userInfo.getPoints());
                loginVO.setTitle(userInfo.getTitle());
                loginVO.setUserLevel(userInfo.getUserLevel());
                loginVO.setExperience(userInfo.getExperience());
                loginVO.setMoney(userInfo.getMoney());

                loginVO.setSex(userInfo.getSex());
                loginVO.setPosition(userInfo.getPosition());
                loginVO.setSign(userInfo.getSign());

                if (BooleanUtils.isEmpty(userInfo.getPassWord())) {
                    loginVO.setHasPassword(false);
                } else {
                    loginVO.setHasPassword(true);
                }

                statusBean.setToken(JwtTokenUtil.generateToken(JwtTokenUtil.serializable(loginVO), 7));
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(UserInfoConstants.LOGIN_SUCCESS);
                statusBean.setData(loginVO);

                return statusBean;
            }
        }catch (Exception e){
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.LOGIN_FAIL + e);
            throw e;
        }
        return statusBean;
    }

    /**
     * 验证码登陆
     * @param requestJson
     * @return
     */
    public StatusBean<?> verifiCodeLogin(JSONObject requestJson) throws Exception{
        StatusBean<LoginVO> statusBean = new StatusBean();
        UserInfoBean userInfo = new UserInfoBean();
        LoginVO loginVO = new LoginVO();
        try {
            String phone = requestJson.getString("phone");
            String verifiCode = requestJson.getString("verifiCode");
            if (BooleanUtils.isEmpty(redisTemplate.opsForValue().get(phone))) {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(UserInfoConstants.CODE_ERROR_MSG);
                return statusBean;
            }
            if (redisTemplate.opsForValue().get(phone).toString().equals(verifiCode)) {
                userInfo.setPhone(phone);
                userInfo = this.selectOne(userInfo);
                if (userInfo == null) {
                    loginVO = this.initUser(phone);
                } else {
                    /**
                     * 构造登陆返回对象
                     */
                    loginVO.setWechatId(userInfo.getWechatId());
                    loginVO.setBackgroundImg(userInfo.getBackgroundImg());
                    loginVO.setImgPath(userInfo.getImgPath());
                    loginVO.setPhone(userInfo.getPhone());
                    loginVO.setUserName(userInfo.getUserName());

                    loginVO.setPoints(userInfo.getPoints());
                    loginVO.setTitle(userInfo.getTitle());
                    loginVO.setUserLevel(userInfo.getUserLevel());
                    loginVO.setExperience(userInfo.getExperience());
                    loginVO.setMoney(userInfo.getMoney());

                    loginVO.setSex(userInfo.getSex());
                    loginVO.setPosition(userInfo.getPosition());
                    loginVO.setSign(userInfo.getSign());


                    if (BooleanUtils.isEmpty(userInfo.getPassWord())) {
                        loginVO.setHasPassword(false);
                    } else {
                        loginVO.setHasPassword(true);
                    }
                }
                statusBean.setToken(JwtTokenUtil.generateToken(JwtTokenUtil.serializable(loginVO),7));
                statusBean.setRespCode(SystemConstants.RESPONSE_SUCCESS);
                statusBean.setRespMsg(UserInfoConstants.LOGIN_SUCCESS);
                statusBean.setData(loginVO);

            } else {
                statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
                statusBean.setRespMsg(UserInfoConstants.CODE_ERROR);
            }
        }catch (Exception e) {
            e.printStackTrace();
            statusBean.setRespCode(SystemConstants.RESPONSE_FAIL);
            statusBean.setRespMsg(UserInfoConstants.LOGIN_FAIL + e);
            throw e;
        }
        return statusBean;
    }

    /**
     * 登陆入口
     * @param requestJson
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StatusBean<?> login(JSONObject requestJson) throws Exception{
        return ("password".equals(requestJson.getString("loginType")))?passwordLogin(requestJson):verifiCodeLogin(requestJson);
    }

    /**
     * 设置性别
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void updateSex(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        userInfoBean.setSex(requestJson.getString("sex"));
        userInfoBean.setUpdateTime(new Date());
        this.saveOrUpdate(userInfoBean);
    }

    /**
     * 设置签名
     * @param requestJson
     * @throws Exception
     */

    @Override
    public void updateSign(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        userInfoBean.setSign(requestJson.getString("sign"));
        userInfoBean.setUpdateTime(new Date());
        this.saveOrUpdate(userInfoBean);
    }

    /**
     * 设置地区
     * @param requestJson
     * @throws Exception
     */
    @Override
    public void updatePosition(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        userInfoBean.setPosition(requestJson.getString("position"));
        userInfoBean.setUpdateTime(new Date());
        this.saveOrUpdate(userInfoBean);
    }

    /**
     * 得到创建用户时间
     * @param requestJson
     * @return
     * @throws Exception
     */
    @Override
    public String getDate(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setWechatId(requestJson.getString("wechatId"));
        userInfoBean = this.selectOne(userInfoBean);
        Date createTime = userInfoBean.getCreateTime();
        Date nowDate = new Date();
        //转为天
       return String.valueOf((nowDate.getTime()-createTime.getTime())/1000/60/60/24);
    }

    @Override
    public Boolean checkPhoneUsed(JSONObject requestJson) throws Exception {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setPhone(requestJson.getString("phone"));
        userInfoBean = this.selectOne(userInfoBean);
        return (BooleanUtils.isEmpty(userInfoBean))?true:false;
    }
}
