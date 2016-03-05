package net.jxquan.dao;


import net.jxquan.entity.CoachPraise;
import net.jxquan.entity.CoachPraise;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="coachPariseDAO")
public class CoachPraiseDAO {
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
    public void addCoachPraise(CoachPraise praise){
    	this.getCurrentSession().save(praise);
    }
    
    /**
	 *删除约车订单
	 */
    @Transactional
    public void deleteCoachPraise(CoachPraise praise){
    	this.getCurrentSession().delete(praise);
    }
    
    /**
	 *根据ID删除CoachPraise
	 */
    @Transactional
    public void deletepraise(long id){
    	CoachPraise praise=(CoachPraise)this.getCurrentSession().get(CoachPraise.class,id);
        this.getCurrentSession().delete(praise);
    }
    
    /**
	 *控制ownStudent的增加(本校学生点赞使用)
	 */
    @Transactional
    public void addOwnStudent(long id){
    	CoachPraise praise=(CoachPraise)this.getCurrentSession().get(CoachPraise.class,id);
        praise.setOwnStudent(praise.getOwnStudent()+1);
    }
    
    /**
	 *控制otherStudent的增加(非本校学生点赞使用)
	 */
    @Transactional
    public void addOtherStudent(long id){
    	CoachPraise praise=(CoachPraise)this.getCurrentSession().get(CoachPraise.class,id);
        praise.setOtherStudent(praise.getOtherStudent()+1);
        praise.setTotalStudent(praise.getTotalStudent()+1);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
