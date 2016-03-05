package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.ExamFour;
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
@Repository(value="examFourDAO")
public class ExamFourDAO {
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
    public void addExamFour(ExamFour ExamFour){
    	this.getCurrentSession().save(ExamFour);
    }
    
    /**
	 *删除科目一题目
	 */
    @Transactional
    public void deleteExamFour(ExamFour ExamFour){
    	this.getCurrentSession().delete(ExamFour);
    }
    
    /**
	 *根据ID删除科目一题目
	 */
    @Transactional
    public void deleteExamFour(long id){
    	ExamFour ExamFour=(ExamFour)this.getCurrentSession().get(ExamFour.class,id);
        this.getCurrentSession().delete(ExamFour);
    }
    
    /**
   	 *更新科目四题目 
   	 */
    @Transactional
    public void updateExamFour(ExamFour ExamFour){
    	ExamFour tempExam=(ExamFour)this.getCurrentSession().get(ExamFour.class, ExamFour.getId());
    	CloneUtils.clone(tempExam, ExamFour);
    	this.getCurrentSession().saveOrUpdate(tempExam);
    }
    
    /**
   	 *根据ID查找试题 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public ExamFour findExamFourByID(long id){
    	return (ExamFour)this.getCurrentSession().get(ExamFour.class, id);
    }
    
    /**
   	 *根据试题类型查找试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findExamFourByType(int examType){
    	return (List<ExamFour>)this.getCurrentSession().createQuery("FROM ExamFour e WHERE e.examType = :examType")
    			.setInteger("examType", examType).list();
    }
    
    /**
   	 *根据难度类型查找试题 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findExamFourByDifficulty(int difficulty){
    	return (List<ExamFour>)this.getCurrentSession().createQuery("FROM ExamFour e WHERE e.difficulty = :difficulty")
    			.setInteger("difficulty",difficulty).list();
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
    
    /**
   	 *查找一定数量的题目 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findNumberedExam(int beginExam,int number){
    	return (List<ExamFour>)this.getCurrentSession().
    			createQuery("FROM ExamFour")
    			.setFirstResult(beginExam)
    			.setMaxResults(number)
    			.list();
    }
    
    /**
   	 *根据给定的ID查找指定 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<ExamFour> findGivenExam(String range){
    	String final_range="("+range+")";
    	return (List<ExamFour>)this.getCurrentSession().
    			createQuery("FROM ExamFour e WHERE e.id IN "+final_range+"")
    			.list();
    }
    
    
    
    
    

}
