package com.myhouse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myhouse.entity.User;


public class UserDao extends BaseDao{
	/**
	 * selUserByName(String name)����ͨ�����������ĳ���û�����
	 * @param name
	 * @return һ���û�����
	 */
	public User selUserByName(String name){
		User us = null;
		String sql = "select * from users where name = ?";
		Object [] values = {name};
		ResultSet rs =  super.exeQuery(sql, values);
		try {
			if(rs.next()){
				us= new User();
				us.setId(rs.getInt("id"));
				us.setIsadmin(rs.getInt("isadmin"));
				us.setName(rs.getString("name"));
				us.setPassword(rs.getString("password"));
				us.setTelephone(rs.getString("telephone"));
				us.setUsername(rs.getString("username"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.closeAll();
		}
		return us;
	}
	/**
	 * selUserIsTrue(String name)����  ���û����ж����ݿ����Ƿ����������
	 * @param name
	 * @return �ж��Ƿ���ڵ�booleanֵ��
	 */
	public boolean selUserIsTrue(String name){
		boolean result = false;
		String sql = "select * from users where name = ?";
		Object [] values = {name};
		ResultSet rs =  super.exeQuery(sql, values);
		try {
			if(rs.next()){
			result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.closeAll();
		}
		return result;
	}
	/**
	 * ����û����û�ע�ᡣ
	 * @param us Ҫ��ӵ��û�����
	 * @return ����Ӱ������
	 */
	public int addUser(User us){
		int result =-2;
		String sql = "insert users(name,password,telephone,username,isadmin) values(?,?,?,?,0)";
		Object [] values = {us.getName(),us.getPassword(),us.getTelephone(),us.getUsername()};
		result =  super.exeUpdate(sql, values);
		return result;
	}
}
