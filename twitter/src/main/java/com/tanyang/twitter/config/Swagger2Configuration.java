package com.tanyang.twitter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    //访问地址http://localhost:8080/swagger-ui.html

    @Bean
    public Docket accessToken(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")//定义组
                .select()//选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.basePackage("com.tanyang.twitter.control"))//拦截的包的路径
                .paths(PathSelectors.regex("/*/.*"))//拦截的接口路径
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("推特系统")//标题
                .description("spring boot 系统Api")//描述
                .termsOfServiceUrl("http://www.extlight.com")
                .contact(new Contact("moonlightL","http://www.extlight.com","1240288959@qq.com"))//联系
                .version("1.0")//版本
                .build();
    }
}