package com.c1se44.school_connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// thêm exclude = {SecurityAutoConfiguration.class } để fix lỗi JPAauditing: default security password
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2
public class SchoolConnectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SchoolConnectApplication.class, args);
	}
	
}
