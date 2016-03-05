package net.jxquan.service;

import java.util.Date;
import java.util.List;

import net.jxquan.dao.CoachDAO;
import net.jxquan.entity.Coach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="coachService")
public class CoachService {
	private CoachDAO coachDAO;

	public CoachDAO getCoachDAO() {
		return coachDAO;
	}
	
    @Autowired
	public void setCoachDAO(CoachDAO coachDAO) {
		this.coachDAO = coachDAO;
	}
    
    @Cacheable(value="coachCache",key="#root.methodName")
    public List<Coach> getAllCoachs(){
    	return coachDAO.findAllCoachs();
    }
    
    @CacheEvict(value="coachCache",allEntries=true)
    public void addCoach(Coach coach){
    	this.getCoachDAO().addCoach(coach);
    }
    
    @CacheEvict(value="coachCache",allEntries=true)
	public void deleteCoach(long CoachID){
    	this.getCoachDAO().deleteCoachByID(CoachID);
	}
    
    @Cacheable(value="coachCache",key="#root.methodName+#schoolID")
	public List<Coach> getCoachForSchool(long schoolID){
		return coachDAO.findCoachsForSchool(schoolID);
	}
    
    public Coach findCoachByID(long CoachID){
    	return coachDAO.findCoachByID(CoachID);
    }
    
}
