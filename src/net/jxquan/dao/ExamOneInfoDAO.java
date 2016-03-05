package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.ExamOne;
import net.jxquan.entity.ExamOneInfo;
import net.jxquan.entity.Image;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author luckyyflu
 * @Ddate 2015/6/5
 */
@Repository(value="examOneInfoDAO")
public class ExamOneInfoDAO {
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
	 * 添加科目一题目
	 */
    @Transactional
    public void addExamOneInfo(ExamOneInfo examOneInfo){
    	this.getCurrentSession().save(examOneInfo);
    }
    
    /**
	 *删除科目一题目
	 */
    @Transactional
    public void deleteExamOneInfo(ExamOneInfo examOne){
    	this.getCurrentSession().delete(examOne);
    }
    
    /**
	 *根据ID删除科目一题目
	 */
    @Transactional
    public void deleteExamOne(long id){
    	ExamOne examOne=(ExamOne)this.getCurrentSession().get(Image.class,id);
        this.getCurrentSession().delete(examOne);
    }
    
    /**
   	 *更新科目一题目 
   	 */
    @Transactional
    public void updateExamOne(ExamOneInfo examOneInfo){
    	this.getCurrentSession().saveOrUpdate(examOneInfo);
    }
    
    /**
   	 *根据ID查找试题 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamOneInfo findExamOneInfoByID(long id){
    	return (ExamOneInfo)this.getCurrentSession().get(ExamOneInfo.class, id);
    }
    
    /**
   	 *根据userID查找相关记录 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamOneInfo findExamOneInfoByUserID(long userID){
    	return (ExamOneInfo)this.getCurrentSession().
    			createQuery("FROM ExamOneInfo e WHERE e.userID = :userID").
    			setLong("userID",userID).uniqueResult();
    			
    }
    
    
    /**
   	 *查找所有试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findAllExamOne(){
    	return (List<ExamOne>)this.getCurrentSession().createQuery("FROM ExamOne ORDER BY id")
    			.list();
    }
    
    /**
   	 *查找所有判断题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findAllJudge(){
    	return (List<ExamOne>)this.getCurrentSession().createQuery("FROM ExamOne e WHERE e.A = null")
    			.list();
    }
    
    /**
   	 *查找所有选择题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findAllSelect(){
    	return (List<ExamOne>)this.getCurrentSession().createQuery("FROM ExamOne e WHERE e.A != null")
    			.list();
    }
    
    
    
    
    

}
