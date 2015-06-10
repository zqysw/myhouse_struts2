package com.myhouse.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myhouse.entity.District;
import com.myhouse.entity.FileInfo;
import com.myhouse.entity.HouseInfo;
import com.myhouse.entity.HouseType;
import com.myhouse.entity.Street;
import com.myhouse.entity.User;
import com.myhouse.myhouseUtils.Datechange;
import com.myhouse.myhouseUtils.GetFileName;
import com.myhouse.service.DistrictService;
import com.myhouse.service.FileInfoService;
import com.myhouse.service.HouseInfoService;
import com.myhouse.service.HouseTypeService;
import com.myhouse.service.StreetService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class PublicAction extends ActionSupport implements RequestAware,SessionAware,Preparable{
	private Map<String, Object> request;
	private Map<String, Object> session;
	private HouseInfo house;
	// myFile属性用来封装上传的文件  
    private File myFile;  
      
    // myFileContentType属性用来封装上传文件的类型  
    private String myFileContentType;  
  
    // myFileFileName属性用来封装上传文件的文件名  
    private String myFileFileName; 

     public File getMyFile() {
		return myFile;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	public String getMyFileContentType() {
		return myFileContentType;
	}
	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}
	public String getMyFileFileName() {
		return myFileFileName;
	}
	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
	
	public HouseInfo getHouse() {
		return house;
	}
	public void setHouse(HouseInfo house) {
		this.house = house;
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;
		
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
	public String publicprepare(){
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HouseTypeService service = new HouseTypeService();
		List<HouseType> list = service.selAllType();
		request.put("type", list);
		DistrictService disservice = new DistrictService();
		List<District> dis = disservice .selDistrict();
		request.put("district", dis);
		StreetService strservice = new StreetService();
		List<Street> street = strservice .selStreet();
		request.put("street", street);
		//request.getRequestDispatcher("public.jsp").forward(request, response);
		return SUCCESS;
		
	
	}
	public String publichouse()throws Exception{
		HttpServletResponse response = 	ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
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
		
	/*	 DiskFileItemFactory factory = new DiskFileItemFactory();
		 factory.setRepository(new File("D:/temp"));
		 ServletFileUpload upload = new ServletFileUpload(factory);
		 */
		/* 
		//得到后缀名ext
		String ext = this.getMyFileContentType();
		//要上传到的路径        
		String upload_parent =ServletActionContext.getServletContext().getRealPath("/upload");
		setMyFileFileName(GetFileName.GetRamdomFileName()+ext);
		File file = new File(upload_parent, this.getMyFileFileName());
		
		InputStream ins;
		FileOutputStream outs;
		try {
			ins = new FileInputStream(file);
		 outs = new FileOutputStream(file);
		 byte [] datas = new byte[1024];
			int len = 0;
			while((len = ins.read(datas))>0){
				outs.write(datas,0,len);
			}
			outs.close();
			ins.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		
		
		 //基于myFile创建一个文件输入流  
        InputStream is = new FileInputStream(myFile);  
          
        // 设置上传文件目录  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
          
        // 设置目标文件  
        File toFile = new File(uploadPath, this.getMyFileFileName());  
          
        // 创建一个输出流  
        OutputStream os = new FileOutputStream(toFile);  
  
        //设置缓存  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
        //读取myFile文件输出到toFile文件中  
        while ((length = is.read(buffer)) > 0) {  
            os.write(buffer, 0, length);  
        }  
 
        System.out.println("上传文件名"+myFileFileName);  
        System.out.println("上传文件类型"+myFileContentType);  
      
        //关闭输出流  
        os.close();  
        //关闭输入流  
        is.close();  
          
		
		String filename=this.getMyFileFileName();
		String filepath = filename;
		HouseInfo house = new HouseInfo(getHouse().getTitle(), htype.getId(),getHouse().getFloorage(),
				getHouse().getPrice(), getHouse().getHousedate(), district.getId(), street.getId(), getHouse().getContact(),
				getHouse().getContent(), name);
		HouseInfoService service = new HouseInfoService();
		int result = service.addHouse(house);
		if (result == 1) {
			//response.sendRedirect("ListServlet?op=list");
		} else if (result == 0) {
			out.print("<script>alert('没有存入数据库');history.go(-1);</script>");
			return null;
		} else if (result == -1) {
			out.print("<script>alert('该房屋已存在，不可重复存储');history.go(-1);</script>");
			return null;
		}
		FileInfo fileinfo = new FileInfo(filename, filepath, name);
		FileInfoService fileservice = new FileInfoService();
		int fileresult = fileservice.insertFile(fileinfo);
		if (fileresult == 1) {
			//response.sendRedirect("ListServlet?op=list");
			return SUCCESS;
		} else if (fileresult == 0) {
			out.print("<script>alert('文件没有存入数据库');history.go(-1);</script>");
			return null;
		} else if (fileresult == -2) {
			out.print("<script>alert('该文件没有进入数据库开展操作');history.go(-1);</script>");
			return null;
		}
	
		return SUCCESS;
	}

}
