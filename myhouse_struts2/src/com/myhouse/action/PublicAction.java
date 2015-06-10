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
	// myFile����������װ�ϴ����ļ�  
    private File myFile;  
      
    // myFileContentType����������װ�ϴ��ļ�������  
    private String myFileContentType;  
  
    // myFileFileName����������װ�ϴ��ļ����ļ���  
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
		//�õ���׺��ext
		String ext = this.getMyFileContentType();
		//Ҫ�ϴ�����·��        
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

		
		
		 //����myFile����һ���ļ�������  
        InputStream is = new FileInputStream(myFile);  
          
        // �����ϴ��ļ�Ŀ¼  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
          
        // ����Ŀ���ļ�  
        File toFile = new File(uploadPath, this.getMyFileFileName());  
          
        // ����һ�������  
        OutputStream os = new FileOutputStream(toFile);  
  
        //���û���  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
        //��ȡmyFile�ļ������toFile�ļ���  
        while ((length = is.read(buffer)) > 0) {  
            os.write(buffer, 0, length);  
        }  
 
        System.out.println("�ϴ��ļ���"+myFileFileName);  
        System.out.println("�ϴ��ļ�����"+myFileContentType);  
      
        //�ر������  
        os.close();  
        //�ر�������  
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
			out.print("<script>alert('û�д������ݿ�');history.go(-1);</script>");
			return null;
		} else if (result == -1) {
			out.print("<script>alert('�÷����Ѵ��ڣ������ظ��洢');history.go(-1);</script>");
			return null;
		}
		FileInfo fileinfo = new FileInfo(filename, filepath, name);
		FileInfoService fileservice = new FileInfoService();
		int fileresult = fileservice.insertFile(fileinfo);
		if (fileresult == 1) {
			//response.sendRedirect("ListServlet?op=list");
			return SUCCESS;
		} else if (fileresult == 0) {
			out.print("<script>alert('�ļ�û�д������ݿ�');history.go(-1);</script>");
			return null;
		} else if (fileresult == -2) {
			out.print("<script>alert('���ļ�û�н������ݿ⿪չ����');history.go(-1);</script>");
			return null;
		}
	
		return SUCCESS;
	}

}
