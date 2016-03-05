package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.ServeRelation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="serveRelationDAO")
public class ServeRelationDAO {

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
	 *增加驾校服务学校 
	 */
    @Transactional
    public void addSchool(ServeRelation schoolServer){
    	this.getCurrentSession().save(schoolServer);
    }
    
    /**
	 *删除驾校所服务的学校
	 */
    @Transactional
    public void deleteUser(ServeRelation schoolServer){
    	this.getCurrentSession().delete(schoolServer);
    }
    
    /**
	 *根据ID删除相应服务学校的信息 
	 */
    @Transactional
    public void deleteServerByID(long id){
    	ServeRelation school=(ServeRelation)this.getCurrentSession().load(ServeRelation.class, id);
    	this.getCurrentSession().delete(school);
    }
    
    /**
   	 *更新服务的学校 
   	 */
    @Transactional
    public void updateSchoolServer(long id,String newCollege){
    	ServeRelation relation=(ServeRelation)getCurrentSession().get(ServeRelation.class,id);
    	relation.setCollege(newCollege);
    	this.getCurrentSession().saveOrUpdate(relation);
    }
    
    /**
   	 *根据ID查找学校
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ServeRelation findServerByID(long id){
    	return (ServeRelation)this.getCurrentSession().get(ServeRelation.class, id);
    }
    
    /**
   	 *根据所给驾校查找驾校所服务的学校
   	 */
    public List<String> findCollegeBySchoolID(long schoolID){
    	return ((List<String>)this.getCurrentSession().
    			createQuery("SELECT s.college FROM ServeRelation s WHERE s.schoolID = :schoolID")
    			.setLong("schoolID", schoolID).list());
	}
    
    
    /**
   	 *根据所给大学查找服务该学校的驾校
   	 */
    public List<Long> findSchoolIDByCollege(String college){
    	return (List<Long>)this.getCurrentSession().
    			createQuery("SELECT s.schoolID FROM ServeRelation s WHERE s.college = :college")
    			.setString("college",college).list();
    }
}
