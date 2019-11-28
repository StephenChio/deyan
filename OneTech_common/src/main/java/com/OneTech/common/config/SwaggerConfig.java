package com.OneTech.common.config;

import com.OneTech.common.field.SystemEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @description swagger配置
 * @date 2018年7月29日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Configuration
@EnableSwagger2
/**开发环境下使用*/
@Profile(value="dev")
public class SwaggerConfig implements WebMvcConfigurer  {
	  
    @Bean
    public Docket createRestApi() {
    	ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name(SystemEnum.authority.toString()).description("header-token字段").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
        
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
        		 .globalOperationParameters(aParameters)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.OneTech.guqinuo"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
    	
        return new ApiInfoBuilder()
                .title("guqinuo系统接口文档")
                .version("1.0")
                .contact(new Contact("wsr", "", "wsr@basehe.com"))
                .build();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    	WebMvcConfigurer.super.addResourceHandlers(registry);
    }
    

}