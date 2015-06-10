package com.myhouse.dao;

import com.myhouse.entity.FileInfo;

public class FileInfoDao extends BaseDao{
	/**
	 * insertFile(FileInfo fileinfo)����������ļ���¼��Ϣ�����ݿ⡣
	 * @param fileinfo ����ӵ��ļ�����
	 * @return ����Ӱ������
	 */
	public int insertFile(FileInfo fileinfo){
		int result =-2;
		String sql = "insert into fileinfo(filename,filepath,createtime,owner) values(?,?,now(),?)";
		Object[] values = {fileinfo.getFilename(),fileinfo.getFilepath(),fileinfo.getOwner()};
		result = super.exeUpdate(sql, values);
		return result;
		
	}
}
