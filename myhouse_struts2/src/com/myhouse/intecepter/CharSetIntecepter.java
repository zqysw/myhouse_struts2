package com.myhouse.intecepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CharSetIntecepter extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = 	ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String result = invocation.invoke();
		return result;
	}

}
