package com.myhouse.action;

import java.io.UnsupportedEncodingException;
import java.net.CookiePolicy;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myhouse.entity.HouseInfo;
import com.myhouse.myhouseUtils.Datechange;
import com.myhouse.service.HouseInfoService;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport implements SessionAware,RequestAware,CookiesAware{
	private Map<String, Object> session;
	private Map<String, Object> request;
	private Map<String, String> cookies;
	private String title;
	private String price;
	private String street_id;
	private String type_id;
	private String floorage;
	private String page;
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStreet_id() {
		return street_id;
	}

	public void setStreet_id(String streetId) {
		street_id = streetId;
	}



	public String getType_id() {
		return type_id;
	}

	public void setType_id(String typeId) {
		type_id = typeId;
	}

	public String getFloorage() {
		return floorage;
	}

	public void setFloorage(String floorage) {
		this.floorage = floorage;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	public void setCookiesMap(Map<String, String> cookies) {
		// TODO Auto-generated method stub
		this.cookies=cookies;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public String search(){
		HouseInfoService service = new HouseInfoService();
		int low_price = 0;
		int high_price = 0;
		int low_floorage = 0;
		int high_floorage = 0;
		if (getTitle() == null) {
			setTitle("");
		}if (getPrice() == null) {
			setPrice("");
		}
		if (getStreet_id() == null) {
			setStreet_id("");
		}
		if (getType_id() == null) {
			setType_id("");
		}
		if (getFloorage() == null) {
			setFloorage("");
		}
		// 如果所有的值都为空，说明不涉及搜索，只是单纯显示所有信息。
		if ("".equals(getTitle()) && "".equals(getPage()) && "".equals(getStreet_id())
				&& "".equals(getType_id()) && "".equals(getFloorage())) {
			String pagestr = getPage();
			int pageindex = 1;
			if (pagestr == null) {
				pageindex = 1;
			}
			if (pagestr != null && !"".equals(pagestr)) {
				pageindex = Integer.valueOf(pagestr);
			}
			int pagecount = service.getPageCount(Datechange.PAGESIZE);
			List<HouseInfo> list = service.selHousesByPage(pageindex,
					Datechange.PAGESIZE);
			
			for (HouseInfo house : list) {
				String dist = service.selDistrictTypeById(house
						.getDistrict_id());
				String str = service.selStreetTypeById(house.getStreet_id());
				String type = service.selHouseTypeById(house.getHousetype());
				request.put("district", dist);
				request.put("street", str);
				request.put("type", type);
			}
			request.put("result", list);
			request.put("pageindex", pageindex);
			request.put("pagecount", pagecount);
			request.put("price", price);
			request.put("street_id", street_id);
			request.put("type_id", type_id);
			request.put("floorage", floorage);
			return SUCCESS;
			//request.getRequestDispatcher("index.jsp")
			//		.forward(request, response);
		} else {
			if ("".equals(getPrice())) {
				low_price = 0;
				high_price = 0;
			} else if ("0-100".equals(getPrice())) {
				low_price = 0;
				high_price = 100;
			} else if ("100-200".equals(getPrice())) {
				low_price = 100;
				high_price = 200;
			} else if ("200-1000000".equals(getPrice())) {
				low_price = 200;
				high_price = 1000000;
			}
			if ("".equals(getStreet_id())) {
				street_id = "";
			}
			if ("".equals(getType_id())) {
				type_id = "";
			}
			if ("".equals(getFloorage())) {
				low_floorage = -1;
				high_floorage = -1;
			} else if ("0-40".equals(getFloorage())) {
				low_floorage = 0;
				high_floorage = 40;
			} else if ("40-500".equals(getFloorage())) {
				low_floorage = 40;
				high_floorage = 500;
			} else if ("500-1000000".equals(getFloorage())) {
				low_floorage = 500;
				high_floorage = 1000000;
			}
			//首先读取cookie的值，判断原先是否已经有值了。
			String key = "";
		
			//System.out.println(cookies.get("keys").toString()); 
			try {
				title = new String(getTitle().getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//设置cookie
			if(!"".equals(getTitle())){
				//cookie不支持中文，所以要先转码
				String t="";
				try {
					t = URLEncoder.encode(title, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!"".equals(key)){
					t=key+","+t;
				}
				Cookie cookiekeys = new Cookie("keys", t);
				cookiekeys.setMaxAge(1000*60*60*24*7);
				cookiekeys.setPath("/");
				//response.addCookie(cookiekeys);
			}
			String pagestr = getPage();
			int pageindex = 1;
			if (pagestr == null) {
				pageindex = 1;
			}
			if (pagestr != null && !"".equals(pagestr)) {
				pageindex = Integer.valueOf(pagestr);
			}
			int pagecount = service.getPageCountByAll(Datechange.PAGESIZE,
					low_price, high_price, getTitle(), getType_id(), getStreet_id(),
					low_floorage, high_floorage);
			if (pagecount == 0) {
				pageindex = pagecount;
				request.put("message", "没有搜索到相关内容");
			}
			if(pagecount!=0){
				List<HouseInfo> list = service.search(pageindex,
						Datechange.PAGESIZE, low_price, high_price, getTitle(), getType_id(),
						getStreet_id(), low_floorage, high_floorage);
				for (HouseInfo house : list) {
					String dist = service.selDistrictTypeById(house
							.getDistrict_id());
					String str = service.selStreetTypeById(house.getStreet_id());
					String type = service.selHouseTypeById(house.getHousetype());
					request.put("district", dist);
					request.put("street", str);
					request.put("type", type);

				}	
				request.put("result", list);
				request.put("title", title);
				request.put("price", price);
				request.put("street_id", street_id);
				request.put("type_id", type_id);
				request.put("floorage", floorage);
				}
			request.put("pageindex", pageindex);
			request.put("pagecount", pagecount);
			//request.getRequestDispatcher("index.jsp")
			//		.forward(request, response);
			return SUCCESS;
		}
		
	}
	public String detail(){
		String id =  getId();
		HouseInfoService service = new HouseInfoService(); 
		HouseInfo house = service.selHouseByID(Integer.valueOf(id)); 
		String housetype =  service.selHouseTypeById(house.getHousetype()); 
		String district = service.selDistrictTypeById(house.getDistrict_id()); 
		String street = service.selStreetTypeById(house.getStreet_id()); 
		request.put("house", house);
		request.put("housetype", housetype);
		request.put("district", district);
		request.put("street", street);
		//request.getRequestDispatcher("details.jsp").forward(request, response);
		return SUCCESS;
	}

	

}
