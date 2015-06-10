package com.myhouse.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
/**
 * �������Ϊ�˽��û������һЩHTML��ǩ�������ַ�����ת����
 * @author zqy
 *
 */
public class MyHttpServletRequest extends HttpServletRequestWrapper{
	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
		// TODO Auto-generated constructor stub
	}
	public String toHtml(String value){
		value=value.replaceAll("&", "&amp;");
		value=value.replaceAll("<", "&lt;");
		value=value.replaceAll(">", "&gt;");
		value=value.replaceAll("\"", "&quot;");
		value=value.replaceAll(" ", "&nbsp;");
		value=value.replaceAll("\n", "<br>");
		return value;
	}
	public String getParameter(String name){
		String value = super.getParameter(name);
		if(value==null){
			value = "";
		}else{
			value=toHtml(value);
		}
		return value;
	}
	
}
