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

public class RegisterAction extends ActionSupport implements Preparable{
	private String name;
	private String password;
	private String telephone;
	private String username;
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


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 注册功能业务
	 * @return
	 */
	public String register(){
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = new User(getName(), getPassword(), getTelephone(), getUsername());
		UserService service = new UserService();
		int result = service.addUser(user);
		if (result == -1) {
			out.print("<script>alert('用户名已存在');history.go(-1);</script>");
			return null;
		} else if (result == 0) {
			out.print("<script>alert('未知原因，注册失败');history.go(-1);</script>");
			return null;
		} else {
			return SUCCESS;
			//request.getRequestDispatcher("regsuccess.jsp").forward(request, response);
			//response.sendRedirect("regsuccess.jsp");
		}
	}



}
