package net.jxquan.dao;

import java.util.ArrayList;
import java.util.List;

import net.jxquan.config.PageConfig;
import net.jxquan.entity.School;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="schoolDAO")
public class SchoolDAO {
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    /**
	 * 得到相关的Session
	 */
    public Session getCurrentSession(){
    	return this.getSessionFactory().getCurrentSession();
    }
    
    /**
	 *增加学校 
	 */
    @Transactional
    public void addSchool(School school){
    	this.getCurrentSession().save(school);
    }
    
    /**
	 *删除学校
	 */
    @Transactional
    public void deleteUser(School school){
    	this.getCurrentSession().delete(school);
    }
    
    /**
	 *根据ID删除相应学校 
	 */
    @Transactional
    public void deleteSchoolByID(long id){
    	School school=(School)this.getCurrentSession().load(School.class, id);
    	this.getCurrentSession().delete(school);
    }
    
    /**
   	 *更新学校 
   	 */
    @Transactional
    public void updateSchoolStatus(School school){
    	this.getCurrentSession()
    	.createQuery("UPDATE School s SET s.status = 1 WHERE s.id = :id")
    	.setLong("id", school.getId()).executeUpdate();
    }
    
    /**
   	 *更新学校 
   	 */
    @Transactional
    public void updateSchool(School school){
    	/*School tempSchool=(School)this.getCurrentSession().get(School.class,school.getId());
    	CloneUtils.clone(tempSchool, school);
    	this.getCurrentSession().saveOrUpdate(tempSchool);*/
    	String sql="UPDATE School s SET s.telephone = ?"
    			+ ",s.mail = ?"
    			+ ",s.address = ? "
    			+ ",s.teacherResource =?"
    			+ ",s.localPrice = ?"
    			+ ",s.foreignPrice = ?"
    			+ ",s.otherPrice = ?"
    			+ ",s.studentPerCar = ?"
    			+ ",s.hardwareResource = ?"
    			+ ",s.durationOne =?"
    			+ ",s.durationTwo = ?"
    			+ ",s.durationThree =?"
    			+ ",s.durationFour = ?"
    			+ ",s.passRateOne = ?"
    			+ ",s.passRateTwo = ?"
    			+ ",s.passRateThree = ?"
    			+ ",s.passRateFour = ?"
    			+ ",s.location = ? "
    			+ "WHERE s.id =?";
    	this.getCurrentSession()
    	.createQuery(sql)
    	.setLong(0, school.getTelephone())
    	.setString(1,school.getMail())
    	.setString(2,school.getAddress())
    	.setString(3, school.getTeacherResource())
    	.setDouble(4,school.getLocalPrice())
    	.setDouble(5,school.getForeignPrice())
    	.setDouble(6,school.getOtherPrice())
    	.setInteger(7,school.getStudentPerCar())
    	.setString(8, school.getHardwareResource())
    	.setDouble(9,school.getDurationOne())
    	.setDouble(10,school.getDurationTwo())
    	.setDouble(11,school.getDurationThree())
    	.setDouble(12,school.getDurationFour())
    	.setDouble(13,school.getPassRateOne())
    	.setDouble(14,school.getPassRateTwo())
    	.setDouble(15,school.getPassRateThree())
    	.setDouble(16,school.getPassRateFour())
    	.setString(17,school.getLocation())
    	.setLong(18, school.getId())
    	.executeUpdate();
    }
    
    /**
   	 *根据ID查找学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public School findSchoolByID(long id){
    	return (School)this.getCurrentSession()
    			.createQuery("FROM School s WHERE s.id = :id")
    			.setLong("id",id).uniqueResult();
    }
    
    /**
   	 *得到所有学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<School> findAllSchools(){
    	return (List<School>)this.getCurrentSession().createQuery("FROM School").list();
    }
    
    /**
   	 *得到所有未审核的学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<School> findUncheckedSchools(){
    	return (List<School>)this.getCurrentSession().createQuery("FROM School s WHERE s.status = 0").list();
    }
    
    /**
   	 *得到所有已经未审核的学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<School> findCheckedSchools(){
    	return (List<School>)this.getCurrentSession()
    			.createQuery("FROM School s WHERE s.status = 1").list();
    }
    
    /**
   	 *根据分页得到相关学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<School> findSchoolsForPage(int curPage,String location){
    	System.out.println(curPage+"----"+location);
    	return (List<School>)this.getCurrentSession().createQuery("FROM School s WHERE s.location = :location AND s.status = 1 ORDER BY s.id")
		.setString("location",location)
    	.setFirstResult((curPage-1)*PageConfig.PAGE_SIZE)
    	.setMaxResults(PageConfig.PAGE_SIZE).list();
    }
    
    /**
   	 *根据电话得到相关学校
   	 */
	public School findSchoolByMobile(Long mobile) {
		return (School)this.getCurrentSession().createQuery("FROM School s WHERE s.mobile = :mobile")
				.setLong("mobile",mobile).uniqueResult();
	}
	
	/**
   	 *根据电话和密码得到相关学校
   	 */
	public School findSchoolByMobileAndPassword(Long mobile,String password) {
		return (School)this.getCurrentSession().createQuery("FROM School s WHERE s.mobile = :mobile AND s.password = :password")
				.setLong("mobile",mobile)
				.setString("password",password)
				.uniqueResult();
	}
	
	/**
   	 *根据学校名相关学校
   	 */
	public School findSchoolByName(String schoolName) {
		return (School)this.getCurrentSession().createQuery("FROM School s WHERE s.schoolName = :schoolName")
				.setString("schoolName",schoolName).uniqueResult();
	}
	
	/**
   	 *根据Cookie得到相关学校
   	 */
	public School findSchoolByCookie(String cookie) {
		return (School)this.getCurrentSession().createQuery("FROM School s WHERE s.cookie = :cookie")
				.setString("cookie",cookie).uniqueResult();
	}
	
	/**
   	 *根据大学得到服务该大学的所有驾校
   	 */
	public List<School> findSchoolByCollege(String college){
		List<Long> schoolIDs=(List<Long>)this.getCurrentSession().createQuery
				("SELECT s.schoolID FROM ServeRelation s WHERE s.college = :college AND s.status = 1")
				.setString("college",college).list();
		List<School> schools=new ArrayList<School>();
		for(Long schoolID:schoolIDs){
			schools.add((School)this.getCurrentSession().get(School.class,schoolID));
		}
		return schools;
	}
	
	/**
   	 *根据关键字获得相关学校
   	 */
	public List<School> findSchoolByKey(String key){
		String finalKey="%"+key+"%";
		return (List<School>)this.getCurrentSession().createQuery("FROM School s WHERE s.schoolName like :key AND s.status = 1")
				.setString("key",finalKey).list();
	}
	
	
	/**
   	 *根据学校位置获得相关学校
   	 */
	public List<Long> findSchoolByLocation(String location){
		return (List<Long>)this.getCurrentSession().createQuery("SELECT id FROM School s WHERE s.location = :location")
				.setString("location", location)
				.list();
	}
	
	
	
	
	
	
    
   
}
