package com.myhouse.myhouseUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public   class Datechange {
	/**
	 * ���ַ���ת��Ϊ��������
	 * @param datestr
	 * @param formatstr
	 * @return
	 */
	public static Date getDateByStr(String datestr,String formatstr){
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(formatstr);
		try {
			date = format.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * ����������ת��Ϊ�ַ���
	 * @param date
	 * @param formatstr
	 * @return
	 */
	public static String getStrByDate(Date date,String formatstr){
		SimpleDateFormat dateformat = new SimpleDateFormat(formatstr);
		return dateformat.format(date);
	}
	//����һ����������ʾÿҳ��ʾ����Ϊ3
	public static final int PAGESIZE = 3;
}
