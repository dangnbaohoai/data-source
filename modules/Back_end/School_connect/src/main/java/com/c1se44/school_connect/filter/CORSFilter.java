package com.c1se44.school_connect.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//  bộ lọc CORS trông giống như sau.
//CORS là một cơ chế cho phép nhiều tài nguyên khác nhau (fonts, Javascript, v.v…)
// của một trang web có thể được truy vấn từ domain khác với domain của trang đó.
// CORS là viết tắt của từ Cross-origin resource sharing
public class CORSFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
		
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
