package net.jxquan.dao;


import java.util.List;

import net.jxquan.entity.Coach;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="coachDAO")
public class CoachDAO {

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
	 *增加教练
	 */
    @Transactional
    public void addCoach(Coach coach){
    	this.getCurrentSession().save(coach);
    }
    
    /**
	 *删除教练 
	 */
    @Transactional
    public void deleteCoach(Coach coach){
    	this.getCurrentSession().delete(coach);
    }
    
    /**
	 *根据ID删除相应教练 
	 */
    @Transactional
    public void deleteCoachByID(long id){
    	Coach coach=(Coach)this.getCurrentSession().get(Coach.class, id);
    	this.getCurrentSession().delete(coach);
    }
    
    /**
	 *更新用户 
	 */
    @Transactional
    public void updateCoach(Coach coach){
    	Coach tempCoach=(Coach)this.getCurrentSession().get(Coach.class,coach.getId());
    	CloneUtils.clone(tempCoach, coach);
    	this.getCurrentSession().saveOrUpdate(tempCoach);
    }
    
    /**
	 *根据ID用户
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Coach findCoachByID(long id){
    	return (Coach)this.getCurrentSession().get(Coach.class, id);
    }
      
    
    /**
	 *查找制定驾校的教练
	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Coach> findCoachsForSchool(long schoolID){
         return (List<Coach>)this.getCurrentSession()
        		 .createQuery("FROM Coach u WHERE u.schoolID = :schoolID")
        		 .setLong("schoolID",schoolID)
        		 .list();
    }
    
    /**
   	 *根据电话名查找用户
   	 */
   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
   public Coach findCoachByMobile(long mobile){
       return (Coach)this.getCurrentSession().createQuery("FROM Coach u WHERE u.mobile = :mobile")
       		.setLong("mobile", mobile).uniqueResult();
   }
    
	  /**
	   	 *查找男性教练
	   	 */
	   @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<Coach> findMenCoach(){
		   return (List<Coach>)this.getCurrentSession()
	        		 .createQuery("FROM Coach u WHERE u.gender = 0").list();
		   
	   }
	   /**
	   	 *查找女性教练
	   	 */
	   @SuppressWarnings("unchecked")
	   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<Coach> findWomen(){
		   return (List<Coach>)this.getCurrentSession()
	        		 .createQuery("FROM Coach u WHERE u.gender = 1").list();
		   
	   }
	   
	   /**
	   	 *查找所有教练
	   	 */
	   @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<Coach> findAllCoachs(){
		   return (List<Coach>)this.getCurrentSession()
	        		 .createQuery("FROM Coach").list();
		   
	   }
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
