package com.c1se44.school_connect.config;

import com.c1se44.school_connect.security.JWT.JwtEntryPoint;
import com.c1se44.school_connect.security.JWT.JwtTokenFilter;
import com.c1se44.school_connect.security.userprincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailService userDetailsService;
	@Autowired
	JwtEntryPoint jwtEntryPoint;
	@Bean
	public JwtTokenFilter jwtTokenFilter(){
		return new JwtTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		// disable csrf
		//.authorizeRequests().antMatchers("/api/auth/**").permitAll() lớp dùng khi auth login sẽ cho phép truy cập
		//	.anyRequest().authenticated() chấp nhận tất cả request khi đã login
		// .exceptionHandling().authenticationEntryPoint(jwtEntryPoint) nếu dăng nhập mà chưa login thì gọi qua class jwtEntryPoint
		//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); quản lý session
		http.cors().disable().csrf().disable()
					.authorizeRequests()
					.antMatchers("/api/auth/**",
								"/chat/**",
								"/v2/api-docs",           // swagger
								"/webjars/**",            // swagger-ui webjars
								"/swagger-resources/**",  // swagger-ui resources
								"/configuration/**",
								"/swagger-ui/**",
								"/swagger-resources/**,",
								"/swagger-resources/configuration/ui",
								"/swagger-resources/configuration/security",
								"/swagger-ui.html",
								"/api/export/**",
								"/api/autoload/**")
					.permitAll()
					//.antMatchers("/api/admin/**").access("hasAnyRole('ADMIN')")
					//.antMatchers("/api/censor/**").access("hasAnyRole('CENSOR','ADMIN')")
					.antMatchers("/api/admin/**").authenticated()
					.antMatchers("/api/censor/**").authenticated()
					.antMatchers("/api/user/**").authenticated()
					//.anyRequest().authenticated()
					.and()
					.exceptionHandling()
					.authenticationEntryPoint(jwtEntryPoint)
					.and()
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}