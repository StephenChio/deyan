package com.OneTech.common.aspect;

import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.HttpServletUtils;
import com.OneTech.common.util.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 吴森荣
 * @version v1.0
 * @description 日志切面
 * @date 2018年10月31日
 * @email wsr@basehe.com
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private static ThreadLocal<LogInfo> threadLocalId = new ThreadLocal<>();

    @Pointcut("execution(* com.OneTech..**.service..*.*(..)) || execution(* com.OneTech.service..*.*(..)) ")
    public void log() {
    }

    /**
     * @Before 在方法执行之前执行
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        Long startTime = new Date().getTime();
        String logId = UUIDUtils.getRandom32();
        LogInfo logInfo = new LogInfo(logId, startTime);
        threadLocalId.set(logInfo);
        try {
            //记录http请求
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                //从request中获取http请求的url/请求的方法类型／响应该http请求的类方法／IP地址／请求中的参数
                //imgPath参数过长不输出
                logger.info("service层请求日志,线程日志id={}\n url={}\n method={}\n ip={}\n class_method={}\n args={}，当前长度为{}，允许输出的参数数据长度为200",
                        logId, request.getRequestURI(), request.getMethod(),
                        HttpServletUtils.getIp(request),
                        joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), transformArgs(joinPoint.getArgs()), joinPoint.getArgs().length);

            } else {
                logger.warn("request 为 null!, 请检查是否接收设备数据， 如不是则service 服务有问题");
            }
        } catch (Exception e) {
            logger.error("切面记录日志异常，{}", e);
        }

    }

    /**
     * 对参数处理，防止参数过长日志输出
     *
     * @param joinPoint
     * @return
     */
    private String transformArgs(Object[] joinPoint) {
        String result = null;
        if (BooleanUtils.isEmpty(joinPoint)) {
            return null;
        }
        if (joinPoint.length > 200) {
            List<Object> list = Arrays.asList(joinPoint);
            for(Object object:list){
            	if(object.toString().length()>=200){
            		return null;
				}
			}
            result = JSONObject.toJSONString(list.subList(0, 200));
            if (result.getBytes().length > 1000) {
                return null;
            }
        } else {
            result = JSONObject.toJSONString(joinPoint);
        }
        return result;
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        LogInfo logInfo = threadLocalId.get();
        Long startTime = logInfo.getTime();
        Long endTime = new Date().getTime();
        logger.info("线程日志id={},耗时={}毫秒,响应data={}", logInfo.getLogId(), (endTime - startTime) / 1000, JSONObject.toJSON(object));
    }


    class LogInfo {

        private String logId;
        private Long time;

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public LogInfo(String logId, Long time) {
            super();
            this.logId = logId;
            this.time = time;
        }


    }
}
