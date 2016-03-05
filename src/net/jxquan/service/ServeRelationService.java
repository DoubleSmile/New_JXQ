package net.jxquan.service;

import java.util.List;

import net.jxquan.dao.ServeRelationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="serveRelationService")
public class ServeRelationService {

	private ServeRelationDAO serveRelationDAO;

	public ServeRelationDAO getServeServeRelationDAO() {
		return serveRelationDAO;
	}
	@Autowired
	public void setServeServeRelationDAO(ServeRelationDAO serveRelationDAO) {
		this.serveRelationDAO = serveRelationDAO;
	}
	
//	@Cacheable(value="serveRelationCache",key="#root.methodName+#schoolID")
	public List<String> getCollegeBySchoolID(long schoolID){
		return this.getServeServeRelationDAO().findCollegeBySchoolID(schoolID);
	}
	
	public List<Long> getSchoolIDByCollege(String college){
		return this.getServeServeRelationDAO().findSchoolIDByCollege(college);
	}
	
	
}
