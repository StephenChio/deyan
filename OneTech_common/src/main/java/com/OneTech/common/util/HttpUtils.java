package com.OneTech.common.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class HttpUtils {

	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	private static final String GET  = "GET";
	private static final String POST = "POST";
	private static String CHARSET = "UTF-8";

	private static Map<String, String> jsonHeader = new HashMap<>();
	
	static{
		jsonHeader.put("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
	}
	
	
	private HttpUtils(){}
	
	/**
	 * https 域名校验
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
	/**
	 * https 证书管理
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;  
		}
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}
	}
	
	private static final SSLSocketFactory SSL_SOCKET_FACTORY = initSSLSocketFactory();
	private static final TrustAnyHostnameVerifier TRUST_ANY_HOSTNAME_VERIFIER = new HttpUtils.TrustAnyHostnameVerifier();

	private static SSLSocketFactory initSSLSocketFactory() {
		try {
			TrustManager[] tm = {new HttpUtils.TrustAnyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setCharSet(String charSet) {
		if (StringUtils.isEmpty(charSet)) {
			throw new IllegalArgumentException("charSet can not be blank.");
		}
		HttpUtils.CHARSET = charSet;
	}
	
	private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException {
		URL connectUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection)conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);
			((HttpsURLConnection)conn).setHostnameVerifier(TRUST_ANY_HOSTNAME_VERIFIER);
		}
		
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		conn.setConnectTimeout(19000);
		conn.setReadTimeout(19000);

		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		
		if (headers != null && !headers.isEmpty()) {
			for (Entry<String, String> entry : headers.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		
		return conn;
	}
	
	/**
	 * Send GET request
	 */
	public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
		HttpURLConnection conn = null;
		String result = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
			conn.connect();
			result = readResponseString(conn);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			log.info("get请求请求url，{} \n请求数据，{} \n 响应数据{}",url,queryParas,result);
			if (conn != null) {
				conn.disconnect();
			}
		}
		return result;
		
	}
	
	public static String get(String url, Map<String, String> queryParas) {
		return get(url, queryParas, null);
	}
	
	public static String get(String url) {
		return get(url, null, null);
	}
	
	/**
	 * Send POST request
	 */
	public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) throws Exception {
		HttpURLConnection conn = null;
		String result = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
			conn.connect();
			
			if (data != null) {
				OutputStream out = conn.getOutputStream();
				out.write(data.getBytes(CHARSET));
				out.flush();
				out.close();
			}
			
			result = readResponseString(conn);
		}
		finally {
			log.info("请求地址地址{}\n 请求数据{}\n 响应数据{}",url , data, result);
			if (conn != null) {
				conn.disconnect();
			}
		}
		return result;
	}
	
	public static String post(String url, Map<String, String> queryParas, String data) throws Exception {
		try {
			return post(url, queryParas, data, null);
		} catch (Exception e) {
			log.error("发起post请求异常：url--【{}】，queryParas--【{}】，data--【{}】",url, queryParas, data,e);
			throw new Exception(e);
		}
		
	}
	
	public static String post(String url, String data, Map<String, String> headers) {
		try {
			return post(url, null, data, headers);
		} catch (Exception e) {
			log.error("发起post请求异常：url--【{}】，headers--【{}】，data--【{}】",url, headers, data,e);
		}
		return null;
	}
	
	public static String post(String url, String data) throws Exception {
		try {
			return post(url, null, data, null);
		} catch (Exception e) {
			log.error("发起post请求异常：url--【{}】，data--【{}】",url, data,e);
			throw new Exception(e);
		}
		
	}
	
	private static String readResponseString(HttpURLConnection conn) throws Exception {
		BufferedReader reader = null;
		StringBuilder ret = null;
		try {
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), CHARSET));
			String line = reader.readLine();
			if (line != null) {
				ret = new StringBuilder();
				ret.append(line);
			} else {
				return "";
			}
			
			while ((line = reader.readLine()) != null) {
				ret.append('\n').append(line);
			}
		}catch(Exception e) {
			log.error("发起请求异常",e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return ret.toString();
	}
	
	/**
	 * Build queryString of the url
	 * @throws UnsupportedEncodingException
	 */
	private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) throws UnsupportedEncodingException {
		if (queryParas == null || queryParas.isEmpty()) {
			return url;
		}
		
		StringBuilder sb = new StringBuilder(url);
		boolean isFirst;
		if (url.indexOf('?') == -1) {
			isFirst = true;
			sb.append('?');
		}
		else {
			isFirst = false;
		}
		
		for (Entry<String, String> entry : queryParas.entrySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				sb.append('&');
			}
			
			String key = entry.getKey();
			String value = entry.getValue();
			if (!StringUtils.isEmpty(value)) {

				value = URLEncoder.encode(value, CHARSET);
			}
			sb.append(key).append('=').append(value);
		}
		return sb.toString();
	}
	
	public static String readData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			StringBuilder ret;
			br = request.getReader();
			
			String line = br.readLine();
			if (line != null) {
				ret = new StringBuilder();
				ret.append(line);
			} else {
				return "";
			}
			
			while ((line = br.readLine()) != null) {
				ret.append('\n').append(line);
			}
			
			return ret.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (br != null) {
				try {br.close();} catch (IOException e) {e.printStackTrace();
                    System.err.println(e.getMessage());}
			}
		}
	}	
	
	public static String postJson(String url, String data) {
		return post(url, data, jsonHeader);
	}
	
	public static String postFile(String url, File file) {

		Map<String, String> headers = new HashMap<>();
		 // 定义数据分隔线
        String boundary = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
		headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);
        HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(url, POST, headers);       
	        OutputStream out = new DataOutputStream(conn.getOutputStream());
	        // 定义最后数据分隔线
	        StringBuilder mediaData = new StringBuilder();
	        mediaData.append("--").append(boundary).append("\r\n");
	        mediaData.append("Content-Disposition: form-data;name=\"data\";filename=\""+ file.getName() + "\"\r\n");
	        mediaData.append("Content-Type:application/octet-stream\r\n\r\n");
	        byte[] mediaDatas = mediaData.toString().getBytes();
	        out.write(mediaDatas);
	        DataInputStream fs = new DataInputStream(new FileInputStream(file));
	        int bytes = 0;
	        byte[] bufferOut = new byte[1024];
	        while ((bytes = fs.read(bufferOut)) != -1) {
	            out.write(bufferOut, 0, bytes);
	        }
	        closeQuietly(fs);
	        // 多个文件时，二个文件之间加入这个
	        out.write("\r\n".getBytes());       
	        byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
	        out.write(endData);
	        out.flush();
	        closeQuietly(out);
	        
	       int code = conn.getResponseCode();	       
	       return 200 == code ?"success": String.valueOf(code);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return "";
       
	}
	
	/**
     * closeQuietly
     * @param closeable 自动关闭
     */
    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    
    public static void main(String[] args) throws Exception {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
        String resp = HttpUtils.post("http://192.168.199.185:8080/acceptTask", JSONObject.toJSONString("{\"jobId\":\"10.253.100.191_e660d623-44f0-47f7-b40b-b2df55409ff1\"}", filter));
        System.out.println(resp);
    }

}
