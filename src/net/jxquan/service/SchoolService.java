package net.jxquan.service;

import java.util.Date;
import java.util.List;

import net.jxquan.dao.SchoolDAO;
import net.jxquan.entity.School;
import net.jxquan.entity.User;
import net.jxquan.util.DB5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

	private SchoolDAO schoolDAO;

	public SchoolDAO getSchoolDAO() {
		return schoolDAO;
	}
	@Autowired
	public void setSchoolDAO(SchoolDAO schoolDAO) {
		this.schoolDAO = schoolDAO;
	}
	
	@Cacheable(value="schoolCache",key="#root.methodName")
	public List<School> getAllIDAndName(){
		return (List<School>)schoolDAO.findAllSchools();
	}
	
	@Cacheable(value="schoolCache",key="#root.methodName")
	public List<School> getAllSchools(){
		return (List<School>)schoolDAO.findAllSchools();
	}
	
	//@Cacheable(value="schoolCache",key="#root.methodName+#schoolID")
	public School getSchoolByID(long schoolID){
		return (School)schoolDAO.findSchoolByID(schoolID);
	}
	//@Cacheable(value="schoolCache",key="#root.methodName+#cookie")
	public School getSchoolByCookie(String cookie){
		return (School)schoolDAO.findSchoolByCookie(cookie);
	}
	
//	@Cacheable(value="schoolCache",key="#root.methodName+#curPage")
	public List<School> getSchoolsForPage(int curPage,String location){
		return (List<School>)schoolDAO.findSchoolsForPage(curPage,location);
	}
	
	@CacheEvict(value="schoolCache",allEntries=true)
	public void register(String mobile,String password,String schoolName){
		School school=new School();
		school.setMobile(Long.valueOf(mobile));
		school.setPassword(password);
		school.setSchoolName(schoolName);
		school.setAddress("x");
		String cookie=DB5Utils.strToMD5(schoolName).substring(1,10);
		if(schoolDAO.findSchoolByCookie(cookie)!=null){
			cookie=DB5Utils.strToMD5(schoolName).substring(2,11);
		}
		school.setCookie(cookie);
		schoolDAO.addSchool(school);
	}
	//@Cacheable(value="schoolCache",key="#root.methodName+#mobile")
	public School findByMobile(String mobile){
    	return schoolDAO.findSchoolByMobile(Long.valueOf(mobile));
    }
    
	//@Cacheable(value="schoolCache",key="#root.methodName+#mobile+#password")
    public School findSchoolByMobileAndPassword(String mobile,String password){
    	return schoolDAO.findSchoolByMobileAndPassword(Long.valueOf(mobile), password);
    }
//	@Cacheable(value="schoolCache",key="#root.methodName+#name")
    public School findSchoolByName(String name){
    	return schoolDAO.findSchoolByName(name);
    }
    
//	@Cacheable(value="schoolCache",key="#root.methodName+#keyWord")
    public List<School> getSchoolByKey(String keyWord){
    	return schoolDAO.findSchoolByKey(keyWord);
    }
	
	@CacheEvict(value="schoolCache",allEntries=true)
    public void acceptSchool(Long schoolID){
		School school=schoolDAO.findSchoolByID(schoolID);
    	schoolDAO.updateSchoolStatus(school);
    }
	
	@CacheEvict(value="schoolCache",allEntries=true)
    public String clearCache(){
		return "success";
    }
	
	@Cacheable(value="schoolCache",key="#root.methodName")
	public List<School> getUnckeckedSchool(){
		return schoolDAO.findUncheckedSchools();
	}
	
	@Cacheable(value="schoolCache",key="#root.methodName")
	public List<School> getCkeckedSchool(){
		return schoolDAO.findCheckedSchools();
	}
	
	public void updateSchool(School school) {
		schoolDAO.updateSchool(school);
	}
	
	public List<Long> findSchoolByLocation(String location){
    	return schoolDAO.findSchoolByLocation(location);
    }
	
}
