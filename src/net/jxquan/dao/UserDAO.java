package net.jxquan.dao;


import java.util.List;

import net.jxquan.entity.User;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="userDAO")
public class UserDAO {

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
	 *增加用户 
	 */
    @Transactional
    public void addUser(User user){
    	this.getCurrentSession().save(user);
    }
    
    /**
	 *删除用户 
	 */
    @Transactional
    public void deleteUser(User user){
    	this.getCurrentSession().delete(user);
    }
    
    /**
	 *根据ID删除相应用户 
	 */
    @Transactional
    public void deleteUserByID(long id){
    	User user=(User)this.getCurrentSession().load(User.class, id);
    	this.getCurrentSession().delete(user);
    }
    
    /**
	 *更新用户 
	 */
    @Transactional
    public void updateUser(User user){
    	User tempUser=(User)this.getCurrentSession().get(User.class,user.getId());
    	CloneUtils.clone(tempUser, user);
    	this.getCurrentSession().saveOrUpdate(tempUser);
    }
    
    /**
	 *更新用户 
	 */
    @Transactional
    public void updateUserMsg(User user){
    	this.getCurrentSession().saveOrUpdate(user);
    }
    
    /**
	 *根据ID用户
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public User findUserByID(long id){
    	return (User)this.getCurrentSession().get(User.class, id);
    }
    
    /**
	 *根据微信名查找用户
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public User findUserByWeChat(String weChat){
        return (User)this.getCurrentSession().createQuery("FROM User u WHERE u.weChat = :weChat")
        		.setString("weChat", weChat).uniqueResult();
    }
   
    
    /**
	 *查找本地用户
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<User> findLocalUsers(){
         return (List<User>)this.getCurrentSession()
        		 .createQuery("FROM User u WHERE u.isLocal = 0").list();
    }
    
    /**
	 *查找外地用户
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<User> findForeignUsers(){
         return (List<User>)this.getCurrentSession()
        		 .createQuery("FROM User u WHERE u.isLocal = 1").list();
    }
    
    /**
   	 *根据电话名查找用户
   	 */
   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
   public User findUserByMobile(long mobile){
       return (User)this.getCurrentSession().createQuery("FROM User u WHERE u.mobile = :mobile")
       		.setLong("mobile", mobile).uniqueResult();
   }
   
   
	   /**
		 *根据电话(账号)和密码查找用户
		 */
	   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public User findUserByMobileAndPassword(long mobile,String password){
	       return (User)this.getCurrentSession().createQuery("FROM User u WHERE u.mobile = :mobile AND u.password = :password")
	       		.setLong("mobile", mobile).setString("password",password).uniqueResult();
	   }
   
     /**
  	 *查找相应年龄段的用户
  	 */
	  @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	  public List<User> findAgedUser(int startAge,int endAge){
	       return (List<User>)this.getCurrentSession()
	      		 .createQuery("FROM User u WHERE u.age >= :startAge AND u.age <= :endAge")
	      		 .setInteger("startAge",startAge).setInteger("endAge", endAge).list();
	  }
    
	  /**
	   	 *查找男性用户
	   	 */
	   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<User> findMen(){
		   return (List<User>)this.getCurrentSession()
	        		 .createQuery("FROM User u WHERE u.gender = 0").list();
		   
	   }
	   /**
	   	 *查找女性用户
	   	 */
	   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<User> findWomen(){
		   return (List<User>)this.getCurrentSession()
	        		 .createQuery("FROM User u WHERE u.gender = 1").list();
		   
	   }
	   
	   /**
	   	 *查找所有用户
	   	 */
	   @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<User> findAllUsers(){
		   return (List<User>)this.getCurrentSession()
	        		 .createQuery("FROM User").list();
		   
	   }
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
