//package com.OneTech.common.util;
//
//import com.OneTech.common.util.redis.StringRedisTemplateUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.Base64Utils;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Random;
//
///**
// * @description 验证码工具类
// * @date 2019年1月11日
// * @author 吴森荣
// * @email wsr@basehe.com
// * @version v1.0
// */
//public class RandomValidateCodeUtil {
//
//    /**
//     * 放到session中的key
//     */
//    public static final String RANDOMCODEKEY= "RANDOMVALIDATECODEKEY";
//
//    /**
//     * 验证码表单key
//     */
//    public static final String PARAMETER = "verifyCode";
//
//    /**
//     * 前后端分离时获取验证码的参数
//     */
//    public static final String VERIFYCODE_PARAMETER = "uuid";
//
//    /**验证码过期时间：10分钟*/
//    public static final Integer expireSeconds = 60*10;
//
////    private String randString = "0123456789";//随机产生只有数字的字符串 private String
//    private static String randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生只有字母的字符串
////    private static String randString = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";//随机产生数字与字母组合的字符串
//
//
//    private static int width = 85;// 图片宽
//    private static int height = 25;// 图片高
//    private static int lineSize = 20;// 干扰线数量
//    private static int stringNum = 4;// 随机产生字符数量
//
//    private static final Logger logger = LoggerFactory.getLogger(RandomValidateCodeUtil.class);
//
//    private static Random random = new Random();
//
//    /**
//     * 获得字体
//     */
//    private static Font getFont() {
//        return new Font("Fixedsys", Font.CENTER_BASELINE, 25);
//    }
//
//    /**
//     * 获得颜色
//     */
//    private static Color getRandColor(int fc, int bc) {
//        if (fc > 255){
//        	fc = 255;
//        }
//        if (bc > 255){
//        	bc = 255;
//        }
//        int r = fc + random.nextInt(bc - fc - 16);
//        int g = fc + random.nextInt(bc - fc - 14);
//        int b = fc + random.nextInt(bc - fc - 18);
//        return new Color(r, g, b);
//    }
//
//    /**
//     * 生成随机图片
//     */
//    public static void getRandcode(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession();
//        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
//        g.fillRect(0, 0, width, height);//图片大小
//        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小
//        g.setColor(getRandColor(110, 133));//字体颜色
//        // 绘制干扰线
//        for (int i = 0; i <= lineSize; i++) {
//            drowLine(g);
//        }
//        // 绘制随机字符
//        String randomString = "";
//        for (int i = 1; i <= stringNum; i++) {
//            randomString = drowString(g, randomString, i);
//        }
//        logger.info(randomString);
//        //将生成的随机字符串保存到session中
//        session.removeAttribute(RANDOMCODEKEY);
//        session.setAttribute(RANDOMCODEKEY, randomString);
//        g.dispose();
//        try {
//            // 将内存中的图片通过流动形式输出到客户端
//            ImageIO.write(image, "JPEG", response.getOutputStream());
//        } catch (Exception e) {
//            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
//        }
//
//    }
//
//    /**
//     * 生成随机图片进行base64编码
//     * @throws IOException
//     */
//    public static String getBase64Randcode(HttpServletRequest request) throws IOException {
//
//    	String uuid = request.getParameter(VERIFYCODE_PARAMETER);
//    	if(StringUtils.isEmpty(uuid)) {
//    		return null;
//    	}
//    	// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
//    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//    	Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
//    	g.fillRect(0, 0, width, height);//图片大小
//    	g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));//字体大小
//    	g.setColor(getRandColor(110, 133));//字体颜色
//    	// 绘制干扰线
//    	for (int i = 0; i <= lineSize; i++) {
//    		drowLine(g);
//    	}
//    	// 绘制随机字符
//    	String randomString = "";
//    	for (int i = 1; i <= stringNum; i++) {
//    		randomString = drowString(g, randomString, i);
//    	}
//    	logger.info(randomString);
//    	g.dispose();
//    	ByteArrayOutputStream bs = new ByteArrayOutputStream();
//    	try {
//    		// 将内存中的图片通过流动形式输出到客户端
//    		ImageIO.write(image, "JPEG", bs);
////    		String imgsrc = Base64.byteArrayToBase64(bs.toByteArray());
//    		String imgsrc = Base64Utils.encodeToString(bs.toByteArray());
//    		StringRedisTemplateUtil.set(uuid, expireSeconds, randomString);
//    		return imgsrc;
//    	} catch (Exception e) {
//    		logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>", e);
//    	}finally {
//    		if(null != bs) {
//    			bs.close();
//    		}
//    	}
//    	return null;
//    }
//    /**
//     * 生成随机图片进行base64编码
//     * @throws IOException
//     */
//    public static String getBase64Randcode(String uuid) throws IOException {
//    	// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
//    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//    	Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
//    	g.fillRect(0, 0, width, height);//图片大小
//    	g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));//字体大小
//    	g.setColor(getRandColor(110, 133));//字体颜色
//    	// 绘制干扰线
//    	for (int i = 0; i <= lineSize; i++) {
//    		drowLine(g);
//    	}
//    	// 绘制随机字符
//    	String randomString = "";
//    	for (int i = 1; i <= stringNum; i++) {
//    		randomString = drowString(g, randomString, i);
//    	}
//    	logger.info(randomString);
//    	g.dispose();
//    	ByteArrayOutputStream bs = new ByteArrayOutputStream();
//    	try {
//    		// 将内存中的图片通过流动形式输出到客户端
//    		ImageIO.write(image, "JPEG", bs);
////    		String imgsrc = Base64.byteArrayToBase64();
//    		String imgsrc = Base64Utils.encodeToString(bs.toByteArray());
//    		StringRedisTemplateUtil.set(uuid, expireSeconds, randomString);
//    		return imgsrc;
//    	} catch (Exception e) {
//    		logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>", e);
//    	}finally {
//    		if(null != bs) {
//    			bs.close();
//    		}
//    	}
//    	return null;
//    }
//
//    /**
//     * 绘制字符串
//     */
//    private static String drowString(Graphics g, String randomString, int i) {
//        g.setFont(getFont());
//        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
//                .nextInt(121)));
//        String rand = String.valueOf(getRandomString(random.nextInt(randString
//                .length())));
//        randomString += rand;
//        g.translate(random.nextInt(3), random.nextInt(3));
//        g.drawString(rand, 13 * i, 16);
//        return randomString;
//    }
//
//    /**
//     * 绘制干扰线
//     */
//    private static void drowLine(Graphics g) {
//        int x = random.nextInt(width);
//        int y = random.nextInt(height);
//        int xl = random.nextInt(13);
//        int yl = random.nextInt(15);
//        g.drawLine(x, y, x + xl, y + yl);
//    }
//
//    /**
//     * 获取随机的字符
//     */
//    public static String getRandomString(int num) {
//        return String.valueOf(randString.charAt(num));
//    }
//
//
//    /**
//     * 校验验证码
//     * @param request
//     * @param session
//     * @return
//     */
////    public static Boolean checkvrifyCode(HttpServletRequest request){
////    	HttpSession session = request.getSession();
////    	String sysCode = (String) session.getAttribute(RANDOMCODEKEY);
////    	//验证码取出验证后马上销毁
////    	session.removeAttribute(RANDOMCODEKEY);
////    	String vCode = request.getParameter(PARAMETER);
////    	if(sysCode.equalsIgnoreCase(vCode)){
////    		return true;
////    	}
////    	return false;
////    }
//
//    /**
//     * 前后端分离时校验验证码
//     * @param request
//     * @param session
//     * @return
//     */
//    public static Boolean checkBase64VerifyCode(HttpServletRequest request){
//    	//验证码取出验证后马上销毁
//    	String uuid = request.getParameter(VERIFYCODE_PARAMETER);
//    	if(!StringUtils.isEmpty(uuid)) {
//
//    		String sysVerifyCode = StringRedisTemplateUtil.get(uuid);
//    		String vCode = request.getParameter(PARAMETER);
//    		if(!StringUtils.isEmpty(sysVerifyCode) && !StringUtils.isEmpty(vCode)) {
//    			StringRedisTemplateUtil.del(uuid);
//    			//忽略大小写匹配
//                return sysVerifyCode.equalsIgnoreCase(vCode);
//    		}
//    	}
//    	return false;
//    }
//    /**
//     * 前后端分离时校验验证码
//     * @param request
//     * @param session
//     * @return
//     */
//    public static Boolean checkBase64VerifyCode(String uuid, String verifyCode){
//    	/**验证码取出验证后马上销毁*/
//    	if(!StringUtils.isEmpty(uuid)) {
//    		String sysVerifyCode = StringRedisTemplateUtil.get(uuid);
//    		if(!StringUtils.isEmpty(sysVerifyCode) && !StringUtils.isEmpty(verifyCode)) {
////    			verifyCodeMap.remove(uuid);
//    			//忽略大小写匹配
//    			return sysVerifyCode.equalsIgnoreCase(verifyCode);
//    		}
//    	}
//    	return false;
//    }
//
//
//}
