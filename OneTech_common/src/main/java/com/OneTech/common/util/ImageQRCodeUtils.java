package com.OneTech.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @program: kzwl
 * @description: 二维码、图片工具类
 * @author: 何成金
 * @create: 2019-09-25 09:35
 **/
public class ImageQRCodeUtils {

    /**
     * 创建含有二维码的宽为300，高为200的图片
     * @param fileDir 图片保存目录
     * @param QRCodeText 二维码内容
     * @param textList  文本信息（最多三行）
     * @return 图片实际物理路径
     */
    public static String createQRCodeImg(String fileDir, String QRCodeText, List<String> textList){
        File file = new File(fileDir);
        if(!file.exists()){
            file.mkdir();
        }
        // 图片上传后的路径
        String savePath = fileDir + File.separator + UUID.randomUUID().toString().replace("-","")+".jpg";

        int imgWidth = 300;
        int imgHeight = 300;
        int QCCodeWidth = 235;
        int QCCodeHeight = 235;

        ByteArrayOutputStream byteArrayOutStream = null;
        ByteArrayInputStream byteArrayInStream = null;
        FileOutputStream fileOutStream = null;

        HashMap hints = new HashMap();
        // 字符集，内容使用的编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 容错等级，H、L、M、Q
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 二维码距离边的空白宽度
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            // 生成二维码对象，传入参数：内容、码的类型、宽高、配置
            BitMatrix bitMatrix =  new MultiFormatWriter().encode(QRCodeText, BarcodeFormat.QR_CODE, QCCodeWidth, QCCodeHeight, hints);
            byteArrayOutStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"png",byteArrayOutStream);
            byteArrayInStream = new ByteArrayInputStream(byteArrayOutStream.toByteArray());
            BufferedImage QRCodeImage = ImageIO.read(byteArrayInStream);

            BufferedImage bi = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            //绘制环境(画笔)
            Graphics2D g2 = (Graphics2D) bi.getGraphics();
            g2.fillRect(0, 0, imgWidth, imgHeight);
            //画二维码
            g2.drawImage(QRCodeImage, imgWidth/2-QCCodeWidth/2, 1, QCCodeWidth, QCCodeWidth,null);

            //设置字体颜色
            g2.setColor(Color.BLACK);
            //向图片上写字符串
            g2.setFont(new Font("宋体", Font.CENTER_BASELINE, 14));

            int tempHeight = QCCodeHeight + 10;
            if(textList!=null && !textList.isEmpty()){
                for(String str : textList){
                    g2.drawString(str, 8, tempHeight);
                    tempHeight = tempHeight + 20;
                }
            }

            fileOutStream = new FileOutputStream(savePath);
            ImageIO.write(bi, "JPEG", fileOutStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileOutStream!=null){
                try {
                    fileOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteArrayInStream!=null){
                try {
                    byteArrayInStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteArrayOutStream!=null){
                try {
                    byteArrayOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return savePath;
    }
    
    public static void main(String[] args) {
    	List<String> strList = new ArrayList<String>();
    	strList.add("货架格编号：123");
    	String savePath = createQRCodeImg("f:/usr/local/upload", "Test0929-WWWLEYETECCOM-3030100008-160150-20190828173535-521116-1000005", strList);
    	System.out.println("savePath: " + savePath);
    	System.out.println("fileName: " + savePath.substring(savePath.lastIndexOf("\\") + 1, savePath.length()));
	}
}
