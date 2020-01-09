package com.OneTech.web;

import com.OneTech.common.mapper.IBaseMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.unit.DataSize;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@EnableAspectJAutoProxy
@EnableAutoConfiguration
@ComponentScan("com.OneTech")
@MapperScan(basePackages = {"com.OneTech.model.mapper"},markerInterface = IBaseMapper.class)
public class WebAppApplication extends SpringBootServletInitializer {

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(DataSize.ofMegabytes(1024));
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(1024));
		return factory.createMultipartConfig();
	}
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WebAppApplication.class);
		app.run(args);
	}

	/**
	 * 为了打包springboot项目
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
}
