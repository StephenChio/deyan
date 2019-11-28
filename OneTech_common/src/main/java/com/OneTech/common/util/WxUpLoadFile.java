package com.OneTech.common.util;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 29087 on 2017/12/20.
 */
public class WxUpLoadFile {

	private static Logger logger = LoggerFactory.getLogger(WxUpLoadFile.class);
	
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

    /**
     * 上传临时素材
     * @param url 图片上传地址
     * @param file 需要上传的文件
     * @return ApiResult
     * @throws IOException
     */
      public static String uploadMedia(String url, MultipartFile file, String params) {
    	try {
    		URL urlGet = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        conn.setUseCaches(false);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("connection", "Keep-Alive");
	        conn.setRequestProperty("user-agent", DEFAULT_USER_AGENT);
	        conn.setRequestProperty("Charsert", "UTF-8");
	        // 定义数据分隔线
	        String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
	        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
	
	        OutputStream out = new DataOutputStream(conn.getOutputStream());
	        // 定义最后数据分隔线
	        StringBuilder mediaData = new StringBuilder();
	        mediaData.append("--").append(BOUNDARY).append("\r\n");
	        mediaData.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getOriginalFilename() + "\"\r\n");
	        mediaData.append("Content-Type:application/octet-stream\r\n\r\n");
	        logger.info("mediaData数据---{}", mediaData.toString());
	        byte[] mediaDatas = mediaData.toString().getBytes();
	        out.write(mediaDatas);
	        DataInputStream fs = new DataInputStream(file.getInputStream());
	        int bytes = 0;
	        byte[] bufferOut = new byte[1024];
	        while ((bytes = fs.read(bufferOut)) != -1) {
	            out.write(bufferOut, 0, bytes);
	        }
	        IOUtils.closeQuietly(fs);
	        // 多个文件时，二个文件之间加入这个
	        out.write("\r\n".getBytes());
	        if (!StringUtils.isBlank(params)) {
	            StringBuilder paramData = new StringBuilder();
	            paramData.append("--").append(BOUNDARY).append("\r\n");
	            paramData.append("Content-Disposition: form-data;name=\"description\";");
	            byte[] paramDatas = paramData.toString().getBytes();
	            out.write(paramDatas);
	            out.write(params.getBytes(Charsets.UTF_8));
	        }
	        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
	        out.write(end_data);
	        out.flush();
	        IOUtils.closeQuietly(out);
	
	        // 定义BufferedReader输入流来读取URL的响应
	        InputStream in = conn.getInputStream();
	        BufferedReader read = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
	        String valueString = null;
	        
	        StringBuffer bufferRes = new StringBuffer();
	        while ((valueString = read.readLine()) != null){
	            bufferRes.append(valueString);
	        }
	        IOUtils.closeQuietly(in);
	        // 关闭连接
	        if (conn != null) {
	            conn.disconnect();
	        }
	        return bufferRes.toString();
    	} catch (Exception e) {
			return null;
		}
    }
      
}
