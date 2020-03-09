package com.OneTech.common.config.webConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description springboot2.0版本WbeMvc配置方式
 * @date 2018年7月29日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Configuration
public class LocalWebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/login");
	}

}
