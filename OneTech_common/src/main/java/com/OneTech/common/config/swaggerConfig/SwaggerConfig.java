package com.OneTech.common.config.swaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @description swagger配置
 * @date 2020年3月10日
 * @author qww
 * @email 779833570@qq.com
 * @version v1.0
 */
@Configuration
@EnableSwagger2
/**开发环境下使用*/
@Profile(value="dev")
public class SwaggerConfig implements WebMvcConfigurer  {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.OneTech"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("得言系统接口")
                .description("单词计数服务 API 接口文档")
                .version("1.0")
                .termsOfServiceUrl("http://www.baidu.com")
                .build();
    }
}