package com.myhouse.myhouseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetFileName {
	/**
	 * GetRamdomFileName()�ǻ�ȡ�ϴ��ļ�ʱ���ɵ��ļ�������֤�����ظ���
	 * @return
	 */
	public static String GetRamdomFileName(){
		Date today = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmssms");
		String filename = "";
		int date = (int)(Math.random()*9000+1000);
		filename = dateformat.format(today)+date;
		return filename;
	}
}
