package net.jxquan.dao;


import java.util.List;

import net.jxquan.entity.SchoolPraise;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="schoolPariseDAO")
public class SchoolPraiseDAO {
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
	 * 添加驾校点赞信息
	 */
    @Transactional
    public void addSchoolPraise(SchoolPraise praise){
    	this.getCurrentSession().save(praise);
    }
    
    /**
	 * 更新驾校点赞信息
	 */
    @Transactional
    public void updateSchoolPraise(SchoolPraise praise){
    	this.getCurrentSession().saveOrUpdate(praise);
    }
    
    
    /**
	 *控制ownStudent的增加(本校学生点赞使用)
	 *//*
    @Transactional
    public void addOwnStudent(long id){
    	SchoolPraise praise=(SchoolPraise)this.getCurrentSession().get(SchoolPraise.class,id);
        praise.setOwnStudent(praise.getOwnStudent()+1);
        this.getCurrentSession().saveOrUpdate(praise);
    }
    
    *//**
	 *控制otherStudent的增加(非本校学生点赞使用)
	 *//*
    @Transactional
    public void addOtherStudent(long id){
    	SchoolPraise praise=(SchoolPraise)this.getCurrentSession().get(SchoolPraise.class,id);
        praise.setOtherStudent(praise.getOtherStudent()+1);
        praise.setTotalStudent(praise.getTotalStudent()+1);
        this.getCurrentSession().saveOrUpdate(praise);
    }*/
    
    /**
	 *删除约车订单
	 */
    @Transactional
    public void deletePraise(SchoolPraise praise){
    	this.getCurrentSession().delete(praise);
    }
    
    /**
	 *根据ID删除SchoolPraise
	 */
    @Transactional
    public void deletePraiseByID(long id){
    	SchoolPraise praise=(SchoolPraise)this.getCurrentSession().get(SchoolPraise.class,id);
        this.getCurrentSession().delete(praise);
    }
    
    /**
	 *根据ID查找SchoolPraise
	 */
    @Transactional
    public SchoolPraise findSchoolPraiseByID(long id){
    	return (SchoolPraise)this.getCurrentSession().get(SchoolPraise.class, id);
    }
    
    /**
	 *根据schoolID得到SchoolPraise的信息
	 */
    @Transactional
    public SchoolPraise findSchoolPraiseBySchoolID(long schoolID){
    	return (SchoolPraise)this.getCurrentSession()
    			.createQuery("FROM SchoolPraise s WHERE s.schoolID = :schoolID")
    			.setLong("schoolID",schoolID).uniqueResult();
    			
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
