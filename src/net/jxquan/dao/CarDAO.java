package net.jxquan.dao;


import java.util.List;

import net.jxquan.entity.Car;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="carDAO")
public class CarDAO {

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
	 *增加新车
	 */
    @Transactional
    public void addCar(Car car){
    	this.getCurrentSession().save(car);
    }
    
    /**
	 *删除车
	 */
    @Transactional
    public void deleteCar(Car car){
    	this.getCurrentSession().delete(car);
    }
    
    /**
	 *根据ID删除相应教练 
	 */
    @Transactional
    public void deleteCarByID(long id){
    	Car car=(Car)this.getCurrentSession().get(Car.class, id);
    	this.getCurrentSession().delete(car);
    }
    
    /**
	 *更新车的信息 
	 */
    @Transactional
    public void updateCar(Car car){
    	Car tempCar=(Car)this.getCurrentSession().get(Car.class,car.getId());
    	CloneUtils.clone(tempCar, car);
    	this.getCurrentSession().saveOrUpdate(tempCar);
    }
    
    /**
	 *根据ID查找相关的车辆
	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Car findCarByID(long id){
    	return (Car)this.getCurrentSession().get(Car.class, id);
    }
      
    
    /**
	 *查找制定驾校的车
	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Car> findCarsForSchool(long schoolID){
         return (List<Car>)this.getCurrentSession()
        		 .createQuery("FROM Car u WHERE u.schoolID = :schoolID")
        		 .setLong("schoolID",schoolID)
        		 .list();
    }
    
    /**
	 *查找制定仍有空闲位置的车
	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Car> findAvailableCars(long schoolID){
         return (List<Car>)this.getCurrentSession()
        		 .createQuery("FROM Car u WHERE u.curSize < u.maxSize AND u.schoolID = :schoolID")
        		 .setLong("schoolID",schoolID)
        		 .list();
    }
    
    
	  /**
	   *查找制定车牌好的车
	   */
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public Car findCarByNumber(String number){
		   return (Car)this.getCurrentSession()
	        		 .createQuery("FROM Car u WHERE u.number = :number")
	        		 .setString("number",number)
	        		 .uniqueResult();
		   
	   }
	 
	   
	   /**
	   	 *查找所有车辆信息
	   	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	   public List<Car> findAllCars(){
		   return (List<Car>)this.getCurrentSession()
	        		 .createQuery("FROM Car").list();
		   
	   }
    
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
