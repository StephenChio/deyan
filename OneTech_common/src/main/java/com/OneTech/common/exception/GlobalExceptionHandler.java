package com.OneTech.common.exception;

import com.OneTech.common.vo.StatusBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 全局异常处理
 * @date 2018年8月6日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Component
@RestControllerAdvice
public class GlobalExceptionHandler {
 
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * 自定义异常处理
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 */
	@ExceptionHandler
	public StatusBean<?> processException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		StackTraceElement[] ele = ex.getStackTrace();
		String className = ele[0].getClassName();
		String methodName = ele[0].getMethodName();
		Integer line = ele[0].getLineNumber();
		logger.info("Exception:{} 发生位置为： {} {} [{}] ",ex.getMessage(),className,methodName,line);
		
		if(ex instanceof ParamException){
			return new StatusBean<>(((ParamException) ex).getRespCode(),((ParamException) ex).getMsgKey());
		}
		
		if (ex instanceof MissingServletRequestParameterException) {
			return new StatusBean<>("400", ex.getMessage());
		}
 
		if (ex instanceof DuplicateKeyException) {
			logger.error("=======违反主键约束：主键重复插入=======");
			return new StatusBean<>("400", "主键重复插入！");
		}
		
		/**
		 * 未知异常
		 */
		logger.error("【系统异常】{} {}",ex.getMessage(),ex);
		return new StatusBean<>("500","SYSTEM_ERROR",ex.getMessage(),null);
	}

}
