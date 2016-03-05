package net.jxquan.service;


import net.jxquan.dao.ExamOneInfoDAO;
import net.jxquan.entity.ExamOneInfo;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service(value="examOneInfoService")
public class ExamOneInfoService {
	private ExamOneInfoDAO examOneInfoDAO;
    
	public ExamOneInfoDAO getExamOneInfoDAO() {
		return examOneInfoDAO;
	}
	
    @Autowired
	public void setExamOneInfoDAO(ExamOneInfoDAO examOneInfoDAO) {
		this.examOneInfoDAO = examOneInfoDAO;
	}
    
    public void addExamOneInfo(ExamOneInfo examOneInfo){
    	this.getExamOneInfoDAO().addExamOneInfo(examOneInfo);
    }
   
    public ExamOneInfo getExamInfoById(long id){
    	return this.getExamOneInfoDAO().findExamOneInfoByUserID(id);
    }
    public ExamOneInfo getExamInfoByUserId(long userID){
    	return this.getExamOneInfoDAO().findExamOneInfoByUserID(userID);
    }
    
    public void updateExamOneInfo(ExamOneInfo examOneInfo){
    	this.getExamOneInfoDAO().updateExamOne(examOneInfo);
    }
    
    
}
