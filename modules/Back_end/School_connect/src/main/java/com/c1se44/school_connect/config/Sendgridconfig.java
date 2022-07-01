package com.c1se44.school_connect.config;


import com.sendgrid.SendGrid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Sendgridconfig {

	private static final String key="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";


	@Bean
	public SendGrid getSendgrid()
	{
		return new SendGrid(key);
	}

}