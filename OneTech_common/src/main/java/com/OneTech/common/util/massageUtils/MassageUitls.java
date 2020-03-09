package com.OneTech.common.util.massageUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import com.OneTech.common.util.massageUtils.massage.CHttpPost;
import com.OneTech.common.util.massageUtils.massage.ConfigManager;
import com.OneTech.common.util.massageUtils.massage.Message;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service("MassageUitls")
@EnableAsync
public class MassageUitls {
    private  SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
    @Value("${userid}")
    String userid;
    // 用户密码
    @Value("${pwd}")
    String pwd;
    //apiKey
    @Value("${apiKey}")
    String apiKey;
    //地址
    @Value("${masterIpAddress}")
    String masterIpAddress;
    @Value("${ipAddress1}")
    String ipAddress1;
    String ipAddress2 = null;
    String ipAddress3 = null;
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Async
    public void sendMassageToSingle(JSONObject requestJson){
        ConfigManager.setIpInfo(masterIpAddress, ipAddress1, ipAddress2, ipAddress3);
        //密码是否加密   true：密码加密;false：密码不加密
        ConfigManager.IS_ENCRYPT_PWD = true;
        boolean isEncryptPwd = ConfigManager.IS_ENCRYPT_PWD;
        try {
            // 参数类
            Message message = new Message();
            // 实例化短信处理对象
            CHttpPost cHttpPost = new CHttpPost();
            // 设置账号   将 userid转成大写,以防大小写不一致
            message.setUserid(userid.toUpperCase());
            //判断密码是否加密。
            //密码加密，则对密码进行加密
            if (isEncryptPwd) {
                // 设置时间戳
                String timestamp = sdf.format(Calendar.getInstance().getTime());
                message.setTimestamp(timestamp);
                // 对密码进行加密
                String encryptPwd = cHttpPost.encryptPwd(message.getUserid(), pwd, message.getTimestamp());
                // 设置加密后的密码
                message.setPwd(encryptPwd);
            } else {
                // 设置密码
                message.setPwd(pwd);
            }
            // 设置手机号码 此处只能设置一个手机号码
            message.setMobile(requestJson.getString("phone"));
            message.setApikey(apiKey);
            // 设置内容
            Random r = new Random();
            String code = String.valueOf(r.nextInt(899999)+100000);
            redisTemplate.opsForValue().set(requestJson.getString("phone"),code,60,TimeUnit.SECONDS);
            message.setContent("您的验证码是"+code+"，在1分钟内有效。如非本人操作请忽略本短信。");
            // 设置扩展号
            message.setExno("11");
            // 用户自定义流水编号
            message.setCustid(String.valueOf(code));
            // 自定义扩展数据
            message.setExdata("abcdef");
            //业务类型
            message.setSvrtype("SMS001");
            // 返回的平台流水编号等信息
            StringBuffer msgId = new StringBuffer();
            // 返回值
            int result = -310099;
            // 发送短信
            result = cHttpPost.singleSend(message, msgId);
            // result为0:成功;非0:失败
//            if (result == 0) {
//                System.out.println("单条发送提交成功！");
//                System.out.println(msgId.toString());
//            } else {
//                System.out.println("单条发送提交失败,错误码：" + result);
//            }
//            System.out.println(Thread.currentThread().getName());
        } catch (Exception e) {
            //异常处理
            e.printStackTrace();
        }
    }
}
