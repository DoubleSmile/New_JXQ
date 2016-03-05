package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.ExamOne;
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
@Repository(value="examOneDAO")
public class ExamOneDAO {
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
    public void addExamOne(ExamOne examOne){
    	this.getCurrentSession().save(examOne);
    }
    
    /**
	 *删除科目一题目
	 */
    @Transactional
    public void deleteExamOne(ExamOne examOne){
    	this.getCurrentSession().delete(examOne);
    }
    
    /**
	 *根据ID删除科目一题目
	 */
    @Transactional
    public void deleteExamOne(long id){
    	ExamOne examOne=(ExamOne)this.getCurrentSession().get(ExamOne.class,id);
        this.getCurrentSession().delete(examOne);
    }
    
    /**
   	 *更新科目一题目 
   	 */
    @Transactional
    public void updateExamOne(ExamOne examOne){
    	ExamOne tempExam=(ExamOne)this.getCurrentSession().get(ExamOne.class, examOne.getId());
    	CloneUtils.clone(tempExam, examOne);
    	this.getCurrentSession().saveOrUpdate(tempExam);
    }
    
    /**
   	 *根据ID查找试题 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamOne findExamOneByID(long id){
    	return (ExamOne)this.getCurrentSession().get(ExamOne.class, id);
    }
    
    /**
   	 *根据试题类型查找试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findExamOneByType(int examType){
    	return (List<ExamOne>)this.getCurrentSession().createQuery("FROM ExamOne e WHERE e.examType = :examType")
    			.setInteger("examType", examType).list();
    }
    
    /**
   	 *根据难度类型查找试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findExamOneByDifficulty(int difficulty){
    	return (List<ExamOne>)this.getCurrentSession().createQuery("FROM ExamOne e WHERE e.difficulty = :difficulty")
    			.setInteger("difficulty",difficulty).list();
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
    
    /**
   	 *查找一定数量的题目 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findNumberedExam(int beginExam,int number){
    	return (List<ExamOne>)this.getCurrentSession().
    			createQuery("FROM ExamOne")
    			.setFirstResult(beginExam)
    			.setMaxResults(number)
    			.list();
    }
    
    /**
   	 *根据给定的ID查找指定 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamOne> findGivenExam(String range){
    	String final_range="("+range+")";
    	return (List<ExamOne>)this.getCurrentSession().
    			createQuery("FROM ExamOne e WHERE e.id IN "+final_range+"")
    			.list();
    }
    
    
    
    
    

}
