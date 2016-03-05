package net.jxquan.service;

import java.util.List;

import net.jxquan.dao.SchoolPraiseDAO;
import net.jxquan.entity.SchoolPraise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="schoolPraiseService")
public class SchoolPraiseService {

	private SchoolPraiseDAO schoolPraiseDAO;

	public SchoolPraiseDAO getSchoolPraiseDAO() {
		return schoolPraiseDAO;
	}
	@Autowired
	public void setSchoolPraiseDAO(SchoolPraiseDAO schoolPraiseDAO) {
		this.schoolPraiseDAO = schoolPraiseDAO;
	}
	
	public SchoolPraise getSchoolPraiseBySchoolID(long schoolID){
		return this.getSchoolPraiseDAO().findSchoolPraiseBySchoolID(schoolID);
	}
	
	public void addSchoolPraise(SchoolPraise schoolPraise){
		this.getSchoolPraiseDAO().addSchoolPraise(schoolPraise);
	}
	
	public void addOwnStudent(long id){
		SchoolPraise praise=this.getSchoolPraiseDAO().findSchoolPraiseByID(id);
        praise.setOwnStudent(praise.getOwnStudent()+1);
        this.getSchoolPraiseDAO().updateSchoolPraise(praise);
	}
	
	public void addOtherStudent(long id) {
		SchoolPraise praise=this.getSchoolPraiseDAO().findSchoolPraiseByID(id);
        praise.setOtherStudent(praise.getOtherStudent()+1);
        praise.setTotalStudent(praise.getTotalStudent()+1);
        this.getSchoolPraiseDAO().updateSchoolPraise(praise);
	}
	
	
}
