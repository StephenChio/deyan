package com.OneTech.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class UploadUtils {
    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     * @Author:
     * @CreateTime:
     */
    public static boolean generateImage(String imgStr, String path) {
        if (imgStr == null) return false;
        if(imgStr.startsWith("data:image/jpeg;base64,")){
            imgStr = imgStr.split(",")[1];
        }
        imgStr = imgStr.replaceAll(" ","+");
//        System.out.println(imgStr);
        try {// 解密
            byte[] b = Base64.decodeBase64(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
