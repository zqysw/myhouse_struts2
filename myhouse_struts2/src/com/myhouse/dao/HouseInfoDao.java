package com.myhouse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myhouse.myhouseUtils.*;
import com.myhouse.entity.District;
import com.myhouse.entity.HouseInfo;
import com.myhouse.entity.HouseType;
import com.myhouse.entity.Street;

public class HouseInfoDao extends BaseDao {
	/**
	 * selHouseTypeNameById(int id)����ͨ������id��ѯ���Ӧ��ֵ��
	 * @param id ���ѯ�ķ���id
	 * @return ���ط���id��Ӧ�ķ���������
	 * ���ݿ��ѯ���Ҫ�ر���Դ��
	 */
	public String selHouseTypeNameById(int id) {
		HouseType ht = null;
		String sql = "select name from type where id = ?";
		Object[] values = { id };
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				ht = new HouseType();
				ht.setId(id);
				ht.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		String result = ht.getName();
		return result;
	}
	/**
	 * selDistrictNameById(int id)������ѯ����id��Ӧ�Ľ�����
	 * @param id ���ѯ�Ľ���id
	 * @return ������
	 */
	public String selDistrictNameById(int id) {
		District dis = null;

		String sql = "select name from district where id = ?";
		Object[] values = { id };
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				dis = new District();
				dis.setId(id);
				dis.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeAll();
		}
		String result = dis.getName();
		return result;
	}
	/**
	 * selStreetNameById(int id)������ѯ�ֵ�id��Ӧ�Ľֵ���
	 * @param id
	 * @return
	 */
	public String selStreetNameById(int id) {
		Street street = null;
		String sql = "select name from street where id = ?";
		Object[] values = { id };
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				street = new Street();
				street.setId(id);
				street.setName(rs.getString("name"));   //rs.getString("name")�Ƕ�ȡ���ݿ��ж�Ӧnameһ�е�ֵ
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			super.closeAll();
		}
		String result = street.getName();
		return result;
	}
	/**
	 * addHouse(HouseInfo house)������ӷ����ķ�����Ϣ
	 * @param house ����ӵķ��ݶ���
	 * @return Ӱ������
	 */
	public int addHouse(HouseInfo house) {
		int result = -2;
		String sql = "insert into houseinfo(title,housetype,floorage,price,housedate,district_id,street_id,phone,content,owner) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		//���ݿ��д�����ڶ�����string����������תΪstring��
		String housedatetest = Datechange.getStrByDate(house.getHousedate(),
				"yyyy-MM-dd");
		Object[] values = { house.getTitle(), house.getHousetype(),
				house.getFloorage(), house.getPrice(), housedatetest,
				house.getDistrict_id(), house.getStreet_id(),
				house.getContact(), house.getContent(),house.getOwner() };
		result = super.exeUpdate(sql, values);
		return result;
	}
	/**
	 * delHouseById(int delId)����ͨ������id��ɾ������
	 * @param delId ��ɾ���ķ���id��
	 * @return ����Ӱ��������
	 */
	public int delHouseById(int delId) {
		int result = -2;
		String sql = "delete from houseinfo where id = ?";
		Object[] values = {delId };
		result = super.exeUpdate(sql, values);
		return result;
	}
	/**
	 * selHouseByTitle(String title)����Ϊͨ��title�ж����ݿ����Ƿ�����ĳ������Ϣ
	 * @param title Ҫ��ѯ��title
	 * @return �����Ƿ��Ѵ��ڵ�booleanֵ��
	 */
	public boolean selHouseByTitle(String title) {
		boolean result = false;
		String sql = "select * from houseinfo where title = ?";  //���ǳ��ڰ�ȫ�Կ���
		Object[] values = { title };
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return result;
	}
	/**
	 * selHouseById(int id)����ͨ��idֵ�����ҷ�����Ϣ
	 * @param id Ҫ���ҵķ���id
	 * @return �Ƿ��ҵ���booleanֵ��
	 */
	public boolean selHouseById(int id) {
		boolean result = false;
		String sql = "select * from houseinfo where id = ?";
		Object[] values = { id };
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
			
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return result;
	}
	/**
	 * ���Ǹ���idֵ����һ��houseinfo����
	 * @param id ����ķ���id��
	 * @return  ����һ��houseinfo�Ķ���
	 */
	public HouseInfo selHouseByHD(int id) {
		HouseInfo house = null;
		String sql = "select * from houseinfo where id = ?";
		Object[] values = { id };
		
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				
			} else {
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return house;
	}
	/**
	 * selAllHouse()�����ǻ�����з���
	 * @return ���ط��ݵ�list����
	 */
	public List<HouseInfo> selAllHouse() {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		String sql = "select * from houseinfo order by id desc";
		ResultSet rs = super.exeQuery(sql, null);
		try {
			while (rs.next()) {
				HouseInfo house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				list.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return list;
	}
	/**
	 * selHousesByPage(int pageindex,int pagesize)������ҳ����ȡ����
	 * @param pageindex ��ǰҳ
	 * @param pagesize  ÿҳ��ʾ�ķ�������
	 * @return ����house�����list����
	 */
	public List<HouseInfo> selHousesByPage(int pageindex,int pagesize) {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		String sql = "select * from houseinfo order by id desc limit ?,?";
		Object [] values = {(pageindex-1)*pagesize,pagesize};
		ResultSet rs = super.exeQuery(sql, values);
		try {
			while (rs.next()) {
				HouseInfo house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				list.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return list;
	}
	/**
	 * ��ҳ���͹ؼ��������ķ�����key�ǹؼ���
	 * @param pageindex ��ǰҳ
	 * @param pagesize ÿҳ��ʾ��Ŀ
	 * @param key �����ؼ���
	 * @return ����house�����list����
	 */
	public List<HouseInfo> selHousesByPage(int pageindex,int pagesize,String key) {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		String sql = "select * from houseinfo";
		List<Object> values = new ArrayList<Object>();
		if(key!=null&&!"".equals(key)){
			sql = sql+" where title like ?";
			values.add("%"+key+"%");
		}
		sql = sql+" order by id desc limit ?,?";
		values.add((pageindex-1)*pagesize);
		values.add(pagesize);
		ResultSet rs = super.exeQuery(sql, values.toArray()); //values.toArray()������list����תΪ�������͡�
		try {
			while (rs.next()) {
				HouseInfo house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				list.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return list;
	}
	/**
	 * �������������һ����������
	 * @param pageindex ��ǰҳ
	 * @param pagesize ÿҳ��ʾ��Ŀ
	 * @param key ����Ĺؼ���
	 * @return ����house�����list����
	 */
	public List<HouseInfo> search
	(int pageindex,int pagesize,int low_price,int high_price,String title,String type_id,String street_id,int low_floorage,int high_floorage) {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		String sql = "select * from houseinfo where 1=1  ";
		List<Object> values = new ArrayList<Object>();
		if(!"".equals(title)){
			sql = sql+" and title like ?";
			values.add("%"+title+"%");
		}
		if(!"".equals(type_id)){
			int ty_id = Integer.valueOf(type_id);
			sql = sql+" and housetype = ?";
			values.add(ty_id);
		}
		if(!"".equals(street_id)){
			int str_id = Integer.valueOf(street_id);
			sql = sql+" and street_id = ?";
			values.add(str_id);
		}
		if(high_price!=0){
				sql = sql+" and price>?&&price<=? order by CAST(price AS SIGNED)";
				values.add(low_price);
				values.add(high_price);
		}
		if(low_floorage!=-1){
			sql = sql+" and floorage>?&&floorage<=? order by CAST(floorage AS SIGNED)";
			values.add(low_floorage);
			values.add(high_floorage);
		}
		sql = sql+" limit ?,?";
		values.add((pageindex-1)*pagesize);
		values.add(pagesize);
		ResultSet rs = super.exeQuery(sql, values.toArray()); //values.toArray()������list����תΪ�������͡�
		try {
			while (rs.next()) {
				HouseInfo house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				list.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return list;
	}
	/**
	 * selHousesByName() �����ݷ�������������
	 * @param pageindex ��ǰҳ
	 * @param pagesize ÿҳ������Ŀ
	 * @return ����house�����list����
	 */
	public List<HouseInfo> selHousesByName(String name ,int pageindex,int pagesize ) {
		List<HouseInfo> list = new ArrayList<HouseInfo>();
		String sql = "select * from houseinfo  where owner = ? order by id desc limit ?,?";
		Object [] values = {name,(pageindex-1)*pagesize,pagesize};
		ResultSet rs = super.exeQuery(sql, values);
		try {
			while (rs.next()) {
				HouseInfo house = new HouseInfo();
				house.setContact(rs.getString("phone"));
				house.setContent(rs.getString("content"));
				house.setDistrict_id(rs.getInt("district_id"));
				house.setFloorage(rs.getString("floorage"));
				house.setHousedate(rs.getDate("housedate"));
				house.setHousetype(rs.getInt("housetype"));
				house.setPrice(rs.getString("price"));
				house.setStreet_id(rs.getInt("street_id"));
				house.setTitle(rs.getString("title"));
				house.setOwner(rs.getString("owner"));
				house.setId(rs.getInt("id"));
				list.add(house);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			super.closeAll();
		}
		return list;
	}
	/**
	 * UpdateHouse(HouseInfo ht)�����޸ķ�����Ϣ
	 * @param ht Ҫ�޸ĵķ��ݶ���
	 * @return ����Ӱ������
	 */
	public int  UpdateHouse(HouseInfo ht){
		int result = -2;
		String sql = "update houseinfo set title = ?,housetype = ?,floorage = ?, price = ?,housedate = ?" +
				",district_id =?,street_id = ?,phone = ?,content = ? ,owner = ? where id =?";
		String housedatetest = Datechange.getStrByDate(ht.getHousedate(),"yyyy-MM-dd");
		Object [] values = {ht.getTitle(),ht.getHousetype(),ht.getFloorage(),ht.getPrice(),housedatetest,ht.getDistrict_id(),
				ht.getStreet_id(),ht.getContact(),ht.getContent(),ht.getOwner(),ht.getId()};
		result = super.exeUpdate(sql, values);
		return result;	
	}
	/**
	 * selHouseCount()�����ǻ�����������ķ��ݵ�������
	 * @return ���ط�������
	 */
	public int selHouseCount(){
		int result = -1;
		String sql = "select count(*) as num from houseinfo ";
		ResultSet rs = super.exeQuery(sql, null);
		try {
			if (rs.next()) {
				 result=rs.getInt("num");
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
	 * �������������õ������������ܼ�¼��
	 * @param key ����Ĺؼ���
	 * @return �����ܼ�¼��
	 */
	public int selHouseCount(String key){
		int result = -1;
		String sql = "select count(*) as num from houseinfo ";
		List values = new ArrayList();
		if(key!=null&&!"".equals(key)){
			sql = sql+" where title like ?";
			values.add("%"+key+"%");
		}
		ResultSet rs = super.exeQuery(sql, values.toArray());
		try {
			if (rs.next()) {
				 result=rs.getInt("num");
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
	 * selHouseCountByAll()�����������õ����������ķ�������
	 * @param low_price
	 * @param high_price
	 * @param title
	 * @param type_id
	 * @param street_id
	 * @param low_floorage
	 * @param high_floorage
	 * @return ���ط�����
	 */
	public int selHouseCountByAll(int low_price,int high_price,String title,String type_id,String street_id,int low_floorage,int high_floorage){
		int result = -1;
		String sql = "select count(*) as num from houseinfo where 1=1  ";
		List<Object> values = new ArrayList<Object>();
		if(!"".equals(title)){
			sql = sql+" and title like ?";
			values.add("%"+title+"%");
		}
		if(!"".equals(type_id)){
			int ty_id = Integer.valueOf(type_id);
			sql = sql+" and housetype = ?";
			values.add(ty_id);
		}
		if(!"".equals(street_id)){
			int str_id = Integer.valueOf(street_id);
			sql = sql+" and street_id = ?";
			values.add(str_id);
		}
		if(high_price!=0){
				sql = sql+" and price>?&&price<=? order by CAST(price AS SIGNED)";
				values.add(low_price);
				values.add(high_price);
		}
		if(low_floorage!=-1){
			sql = sql+" and floorage>?&&floorage<=? order by CAST(floorage AS SIGNED)";
			values.add(low_floorage);
			values.add(high_floorage);
		}
		ResultSet rs = super.exeQuery(sql, values.toArray());
		try {
			if (rs.next()) {
				 result=rs.getInt("num");
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
	 * �����û�����ø��û���������Ϣ����
	 * @param name
	 * @return ��������Ϣ��������
	 */
	public int selHouseCountByName(String name){
		int result = -1;

		String sql = "select count(*) as num from houseinfo where owner = ?";
		Object [] values = {name};
		ResultSet rs = super.exeQuery(sql, values);
		try {
			if (rs.next()) {
				 result=rs.getInt("num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			super.closeAll();
		}
	return result;
		
	}

}
