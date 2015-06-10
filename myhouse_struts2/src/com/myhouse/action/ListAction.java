package com.myhouse.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myhouse.entity.District;
import com.myhouse.entity.HouseInfo;
import com.myhouse.entity.HouseType;
import com.myhouse.entity.Street;
import com.myhouse.entity.User;
import com.myhouse.myhouseUtils.Datechange;
import com.myhouse.service.DistrictService;
import com.myhouse.service.HouseInfoService;
import com.myhouse.service.HouseTypeService;
import com.myhouse.service.StreetService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ListAction extends ActionSupport implements Preparable,SessionAware,RequestAware{
	private Map<String, Object> session;
	private Map<String, Object> request;
	private String page;
	private HouseInfo house;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HouseInfo getHouse() {
		return house;
	}

	public void setHouse(HouseInfo house) {
		this.house = house;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}

	public void setRequest(Map<String, Object> request) {
		this.request =request;
		
	}
	/**
	 * list()方法用于显示个人发布信息列表
	 * @return
	 */
	public String list(){
		String pagestr = getPage();
		int pageindex = 1;
		if (pagestr == null) {
			pageindex = 1;
		}
		if (pagestr != null && !"".equals(pagestr)) {
			pageindex = Integer.valueOf(pagestr);
		}
		//HttpSession session = request.getSession();
		Object object = session.get("loginuser");
		User us = (User) object;
		String name = us.getName();
		HouseInfoService service = new HouseInfoService();
		int pagecount = service.getPageCountByName(Datechange.PAGESIZE, name);
		List<HouseInfo> list = service.selHousesByName(name, pageindex,
				Datechange.PAGESIZE);
		for (HouseInfo house : list) {
			String dist = service.selDistrictTypeById(house.getDistrict_id());
			String str = service.selStreetTypeById(house.getStreet_id());
			request.put("district", dist);
			request.put("street", str);
		}
		//读取cookie的值
		 HttpServletRequest request = ServletActionContext.getRequest();
		Cookie [] cookies =  request.getCookies();
		for (Cookie cookie : cookies) {
			if("keys".equals(cookie.getName())){
				String key = cookie.getValue();
				try {
					key = URLDecoder.decode(key, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String [] keys = key.split(",");
				request.setAttribute("cookie", keys);	
			}
		}
		request.setAttribute("houselist", list);
		request.setAttribute("pagecount", pagecount);
		request.setAttribute("pageindex", pageindex);
		//request.getRequestDispatcher("list.jsp").forward(request, response);
		return SUCCESS;
	}
	/**
	 * del()方法用于删除房屋信息
	 * @return
	 */
	public String del(){
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out =null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int delId = Integer.valueOf(getId());
		HouseInfoService service = new HouseInfoService();
		int result = service.delHouse(delId);
		if (result == 1) {
			out.print("<script>alert('删除成功');</script>");
			//response.sendRedirect("ListServlet?op=list");
			return SUCCESS;
		} else if (result == 0) {
			out.print("<script>alert('其他原因，删除失败');history.go(-1);</script>");
			return null;
		} else if (result == -1) {
			out.print("<script>alert('数据库中没有这条数据，删除失败');history.go(-1);</script>");
			return null;
		}
		return SUCCESS;
	}
	public String editprepare(){
		//HttpServletRequest request = ServletActionContext.getRequest();
		HouseInfoService service = new HouseInfoService();
		HouseInfo house = service.selHouseByID(Integer.valueOf(getId()));
		HouseTypeService htypeservice = new HouseTypeService();
		List<HouseType> htlist = htypeservice.selAllType();
		HouseType htype= htypeservice.selHouseTypeById(house.getHousetype());
		DistrictService disservice = new DistrictService();
		List<District> dislist = disservice.selDistrict();
		District seldistrict = disservice.selDistrictById(house.getDistrict_id());
		StreetService strservice = new StreetService();
		Street selstreet = strservice.selStreetById(house.getStreet_id());
		List<Street> strlist = strservice.selStreet();
		request.put("htlist", htlist);
		request.put("dislist", dislist);
		request.put("seldistrict",seldistrict );
		request.put("strlist", strlist);
		request.put("selstreet", selstreet);
		request.put("htype", htype);
		request.put("result", house);
		//request.getRequestDispatcher("edithouse.jsp").forward(request, response);
		return SUCCESS;
	}
	public String edit(){
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out =null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object object = session.get("loginuser");
		User us = (User) object;
		String name = us.getName();
		HouseType htype = new HouseType();
		htype.setId(Integer.valueOf(getHouse().getHousetype()));
		//getHouse().getHousedate();
		//Date housedate = Datechange.getDateByStr(getHouse().getHousedate(), "yyyy-MM-dd");
		District district = new District();
		district.setId(Integer.valueOf(getHouse().getDistrict_id()));
		Street street = new Street();
		street.setId(Integer.valueOf(getHouse().getStreet_id()));
		HouseInfo house = new HouseInfo(getHouse().getId(), getHouse().getTitle(), htype.getId(),getHouse().getFloorage(),
				getHouse().getPrice(), getHouse().getHousedate(), district.getId(), street.getId(), getHouse().getContact(),
				getHouse().getContent(), name);
		HouseInfoService service = new HouseInfoService();
		int result = service.editHouse(house);
		if (result == 1) {
			//response.sendRedirect("ListServlet?op=list");
			return SUCCESS;
		} else if (result == 0) {
			out.print("<script>alert('没有存入数据库');history.go(-1);</script>");
			return null;
		} else if (result == -1) {
			out.print("<script>alert('没有找到该房屋信息');history.go(-1);</script>");
			return null;
		}
		return SUCCESS;
	}
}
