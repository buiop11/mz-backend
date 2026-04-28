package com.matjzing.xss;

import com.matjzing.util.FilterUtil;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class XssRequestFilter extends FilterUtil implements Filter  {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest) request).getServletPath();
		if (isFilterWhiteList(path)) {
			chain.doFilter(request, response);
		} else {
			XssRequestWrapper xssRequestWrapper = new XssRequestWrapper((HttpServletRequest)request);
			chain.doFilter(xssRequestWrapper, response);
		}
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig config) {}

}
