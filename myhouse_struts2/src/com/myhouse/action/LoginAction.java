package com.myhouse.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myhouse.entity.User;
import com.myhouse.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class LoginAction extends ActionSupport implements Preparable,RequestAware,SessionAware{
	private String name;
	private String password;
	private Map<String, Object> request;
	private Map<String, Object> session;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void prepare() throws Exception {
		UserService service = new UserService();
	}
	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	public String login(){
		//response.setContentType("text/html;charset=utf-8");
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//request.setCharacterEncoding("utf-8");
		UserService service = new UserService();
		User user = new User(name, password);
		boolean result = service.selUser(user);
		if (result == true) {
			User loginuser = service.getUser(name);
			session.put("loginuser", loginuser);
			//response.sendRedirect("ListServlet?op=list");
			return SUCCESS;
		} else {
			out.print("<script>alert('用户名或密码错误');history.go(-1);</script>");
			return null;
		}
	}
}
