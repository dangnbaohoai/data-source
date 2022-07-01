package com.c1se44.school_connect.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration{
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);
	@RequestMapping(value = "/docs", method = RequestMethod.GET)
	public String docs(){
//        return "forward:/swagger-ui.html";
		return "redirect:/swagger-ui.html";
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(
					"My Capstone REST API",
					"Some custom description of API.",
					"1.0",
					"Terms of service",
					new Contact("Nguyen Thanh Phu", "https://github.com/PhuNguyenThang", "thangphu104@gmail.com"),
					"License of API",
					"https://github.com/springfox/springfox/blob/master/LICENSE",
					Collections.emptyList());
	}
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.groupName("school-connect-api")
					.apiInfo(apiInfo())
					//.securityContexts(Arrays.asList(securityContext()))
					//.securitySchemes(Arrays.asList(apiKey()))
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.c1se44.school_connect.API"))
					.paths(PathSelectors.any())
					.build();
		
	}

	
	//	private SecurityContext securityContext() {
//		return SecurityContext.builder().securityReferences(defaultAuth()).build();
//	}
	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
	
	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "bearer");
	}
}