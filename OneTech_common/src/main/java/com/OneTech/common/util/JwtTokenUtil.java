package com.OneTech.common.util;

import com.OneTech.common.vo.LoginVO;
import com.OneTech.common.vo.token.UserTokenVO;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.Date;

/**
 * @author 吴森荣
 * @version v1.0
 * @description jwt工具类
 * @date 2018年9月18日
 * @email wsr@basehe.com
 */
public class JwtTokenUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String TOKEN_KEY = "STEPHENQWW19971225APTX4869";

    /**
     * @param subject           明文
     * @param expirationSeconds 过期秒数
     * @return
     * @throws ParseException
     */
    public static String generateToken(String subject, int expirationSeconds) throws Exception {
        try {
            Date expireDate = DateUtils.addMinutes(new Date(), expirationSeconds / 1000 * 60);
            String token = Jwts.builder()
                    .setClaims(null)
                    .setSubject(subject)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, TOKEN_KEY) // 不使用公钥私钥
                    .compact();
            logger.info("JwtTokenUtil--subject，{}，expirationSeconds，{}，salt，{}，token，{}", subject, DateUtils.dateToStr(expireDate, "yyyy-MM-dd HH:mm:ss"), TOKEN_KEY, token);
            return token;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * @param token 密文
     * @return
     */
    public static String parseToken(String token) throws Exception{
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(TOKEN_KEY) // 不使用公钥私钥
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
            logger.error("token解密异常，token:{},salt:{}", token, TOKEN_KEY);
            throw e;
        }
        logger.info("JwtTokenUtil--token,{},salt,{},subject,{}", token, TOKEN_KEY, subject);
        return subject;
    }

    /**
     * 校验token并返回用户信息对象
     *
     * @param jwtToken
     * @param
     * @return
     */
    public static Object serializeToObject(String jwtToken) throws Exception {
        try {
            String result = JwtTokenUtil.parseToken(jwtToken);
            if (BooleanUtils.isNotEmpty(result)) {
                /**字符串反序列化成对象*/
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result.getBytes("ISO-8859-1"));
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object object = objectInputStream.readObject();
                objectInputStream.close();
                byteArrayInputStream.close();
                return object;
            }
        }
        catch (Exception e){
            throw e;
        }
        return null;
    }

    /**
     *
     */
    public static String serializable(LoginVO loginVO) throws Exception {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream;
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(loginVO);
            String string = byteArrayOutputStream.toString("ISO-8859-1");
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return string;
        }catch (Exception e) {
            throw e;
        }
    }

    public static String updateToken(JSONObject requestJson){
        try {
            return JwtTokenUtil.generateToken(JwtTokenUtil.serializable((LoginVO) JwtTokenUtil.serializeToObject(requestJson.getString("token"))),10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
