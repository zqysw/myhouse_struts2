package com.myhouse.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * MyParameterFilter�������ǽ��û������һЩHTML��ǩ�������ַ�����ת����ʹ�䲻�ᱻ����ʶ�𣬴Ӷ�������ʾ��
 * @author zqy
 *
 */
public class MyParameterFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new MyHttpServletRequest((HttpServletRequest)request), response);
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
