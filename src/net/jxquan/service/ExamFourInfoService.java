package net.jxquan.service;


import net.jxquan.dao.ExamFourInfoDAO;
import net.jxquan.entity.ExamFourInfo;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service(value="examFourInfoService")
public class ExamFourInfoService {
	private ExamFourInfoDAO ExamFourInfoDAO;
    
	public ExamFourInfoDAO getExamFourInfoDAO() {
		return ExamFourInfoDAO;
	}
	
    @Autowired
	public void setExamFourInfoDAO(ExamFourInfoDAO ExamFourInfoDAO) {
		this.ExamFourInfoDAO = ExamFourInfoDAO;
	}
    
    public void addExamFourInfo(ExamFourInfo ExamFourInfo){
    	this.getExamFourInfoDAO().addExamFourInfo(ExamFourInfo);
    }
   
    public ExamFourInfo getExamInfoById(long id){
    	return this.getExamFourInfoDAO().findExamFourInfoByUserID(id);
    }
    
    public ExamFourInfo getExamInfoByUserId(long userID){
    	return this.getExamFourInfoDAO().findExamFourInfoByUserID(userID);
    }
    
    public void updateExamFourInfo(ExamFourInfo ExamFourInfo){
    	this.getExamFourInfoDAO().updateExamFour(ExamFourInfo);
    }
    
    
}
