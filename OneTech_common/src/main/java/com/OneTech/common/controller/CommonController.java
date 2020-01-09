package com.OneTech.common.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Map.Entry;

@Scope(value="prototype")
public class CommonController {
	
	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected HttpServletRequest request ;
	protected HttpServletResponse response ;
	
	/**
	 * controller访问初始化request,response
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	protected void initHttp(HttpServletRequest request ,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	/**
	 * 将request--Map数组转成JsonObject
	 * @param request
	 * @return
	 */
//	@Synchronized
	public JSONObject getRequestJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, String[]> requestMap = request.getParameterMap();
			for(Entry<String, String[]> entry : requestMap.entrySet()){
				String name = entry.getKey();
				Object value = request.getParameter(name);
				if(null != value){
					jsonObject.put(name, value);
				}
			}
			logger.debug("接收请求request:{}",jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Map数组转换为json异常：{}",e);
		}
		return jsonObject;
	}
	
	/**
	 * 根据key获取session中存放的JSONObject
	 * @param key
	 * @return
	 */
	public JSONObject getJsonAttribute(String key){
		return (JSONObject) request.getSession().getAttribute(key);
	}
	
	
	/**
	 * 获取输入流信息字符串
	 * @return
	 */
	public String getInputStream(){
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			String line = null;
			while((line = reader.readLine() )!=null){
				builder.append(line);
			}
			logger.info("获取输入流数据,{}",builder.toString());
		} catch (Exception e) {
			logger.debug("获取输入流异常,{}",e);
		}finally {
			if(null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭输入流异常,{}",e);
				}
			}
		}
		return builder.toString();
	}
	/**
	 * 获取输入流信息字符串
	 * @return
	 */
	public JSONObject getInputStreamJson(){
		StringBuilder builder = new StringBuilder();
		JSONObject json = new JSONObject();
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			String line = null;
			while((line = reader.readLine() )!=null){
				builder.append(line);
			}
			if(!StringUtils.isEmpty(builder.toString())){
				logger.info("获取输入流数据,{}",builder.toString());
				json = JSONObject.parseObject(builder.toString());
			}
		} catch (Exception e) {
			logger.error("获取输入流异常,{}",e);
		}finally {
			if(null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭输入流异常,{}",e);
				}
			}
		}
		return json;
	}
	

}
