//package com.OneTech.common.util;
//
//import RedisKeyConstants;
//import com.OneTech.common.util.redis.StringRedisTemplateUtil;
//import UserTokenVO;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.ParseException;
//import java.util.Date;
//
///**
// * @description jwt工具类
// * @date 2018年9月18日
// * @author 吴森荣
// * @email wsr@basehe.com
// * @version v1.0
// */
//public class JwtTokenUtil {
//
//	private static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
//
//    /**
//     * @param subject 明文
//     * @param expirationSeconds 过期秒数
//     * @param salt 密码/盐值
//     * @return
//     * @throws ParseException
//     */
//    public static String generateToken(String subject, int expirationSeconds, String salt) throws Exception {
//    	Date expireDate = DateUtils.addMinutes(new Date(), expirationSeconds/1000*60);
//    	String token = Jwts.builder()
//                .setClaims(null)
//                .setSubject(subject)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, salt) // 不使用公钥私钥
//                .compact();
//    	logger.info("JwtTokenUtil--subject，{}，expirationSeconds，{}，salt，{}，token，{}",subject,DateUtils.dateToStr(expireDate, "yyyy-MM-dd HH:mm:ss"),salt,token);
//        return token;
//    }
//
//    /**
//     * @param token 密文
//     * @param salt 密码/盐值
//     * @return
//     */
//    public static String parseToken(String token, String salt) {
//    	String subject = null;
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(salt) // 不使用公钥私钥
//                    .parseClaimsJws(token).getBody();
//            subject = claims.getSubject();
//        } catch (Exception e) {
//        	logger.error("token解密异常，token:{},salt:{}",token,salt);
//        }
//        logger.info("JwtTokenUtil--token,{},salt,{},subject,{}",token,salt,subject);
//        return subject;
//    }
//
//    /**
//     * 校验token并返回用户信息对象
//     * @param authority
//     * @param jwtToken
//     * @param redisKey
//     * @return
//     */
//    public static UserTokenVO validateToken(String authority, String jwtToken) throws Exception {
//    	if(BooleanUtils.isNotEmpty(authority)) {
//			String result = JwtTokenUtil.parseToken(authority, jwtToken);
//			if(BooleanUtils.isNotEmpty(result)) {
//				/**字符串反序列化成对象*/
//				UserTokenVO userTokenVO = UserTokenVO.transform(result);
//				if(BooleanUtils.isNotEmpty(userTokenVO)) {
//					String sysToken = StringRedisTemplateUtil.get(RedisKeyConstants.JWT_KEY  + userTokenVO.getAccountId() + RedisKeyConstants.SEPARATOR + userTokenVO.getBelongSys());
//					if(BooleanUtils.isNotEmpty(sysToken) && sysToken.equals(authority)) {
//						return userTokenVO;
//					}
//				}
//			}
//		}
//    	return null;
//    }
//
//
//}
