package com.myhouse.intecepter;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckIntecepter extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		Map<String, Object> session =  invocation.getInvocationContext().getSession();
		if(session.get("loginuser")!=null){
			return invocation.invoke();
		}else{
			return Action.LOGIN;
		}
		
	}

}
