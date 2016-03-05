package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.ExamFour;
import net.jxquan.entity.ExamFourInfo;
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
@Repository(value="examFourInfoDAO")
public class ExamFourInfoDAO {
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
	 * 添加科目四题目
	 */
    @Transactional
    public void addExamFourInfo(ExamFourInfo ExamFourInfo){
    	this.getCurrentSession().save(ExamFourInfo);
    }
    
    /**
	 *删除科目四题目
	 */
    @Transactional
    public void deleteExamFourInfo(ExamFourInfo ExamFour){
    	this.getCurrentSession().delete(ExamFour);
    }
    
    /**
	 *根据ID删除科目四题目
	 */
    @Transactional
    public void deleteExamFour(long id){
    	ExamFour ExamFour=(ExamFour)this.getCurrentSession().get(Image.class,id);
        this.getCurrentSession().delete(ExamFour);
    }
    
    /**
   	 *更新科目一题目 
   	 */
    @Transactional
    public void updateExamFour(ExamFourInfo ExamFourInfo){
    	this.getCurrentSession().saveOrUpdate(ExamFourInfo);
    }
    
    /**
   	 *根据ID查找试题 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamFourInfo findExamFourInfoByID(long id){
    	return (ExamFourInfo)this.getCurrentSession().get(ExamFourInfo.class, id);
    }
    
    /**
   	 *根据userID查找相关记录 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamFourInfo findExamFourInfoByUserID(long userID){
    	return (ExamFourInfo)this.getCurrentSession().
    			createQuery("FROM ExamFourInfo e WHERE e.userID = :userID").
    			setLong("userID",userID).uniqueResult();
    			
    }
    
    
    /**
   	 *查找所有试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findAllExamFour(){
    	return (List<ExamFour>)this.getCurrentSession().createQuery("FROM ExamFour ORDER BY id")
    			.list();
    }
    
    /**
   	 *查找所有判断题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findAllJudge(){
    	return (List<ExamFour>)this.getCurrentSession().createQuery("FROM ExamFour e WHERE e.A = null")
    			.list();
    }
    
    /**
   	 *查找所有选择题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findAllSelect(){
    	return (List<ExamFour>)this.getCurrentSession().createQuery("FROM ExamFour e WHERE e.A != null")
    			.list();
    }
    
    
    
    
    

}
