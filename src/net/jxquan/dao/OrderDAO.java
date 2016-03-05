package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.Order;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="orderDAO")
public class OrderDAO {
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
	 * 添加Order
	 */
    @Transactional
    public void addOrder(Order order){
    	this.getCurrentSession().save(order);
    }
    
    /**
	 *删除Order
	 */
    @Transactional
    public void deleteOrder(Order order){
    	this.getCurrentSession().delete(order);
    }
    
    /**
	 *根据ID删除Order
	 */
    @Transactional
    public void deleteOrder(long id){
    	Order order=(Order)this.getCurrentSession().load(Order.class,id);
        this.getCurrentSession().delete(order);
    }
    
    /**
   	 *更新订单 
   	 */
    @Transactional
    public void updateOrder(Order order){
    	Order tempOrder=(Order)this.getCurrentSession().get(Order.class, order.getId());
    	CloneUtils.clone(tempOrder, order);
    	this.getCurrentSession().saveOrUpdate(tempOrder);
    }
    
    /**
   	 *根据ID查找订单 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Order findOrderByID(long id){
    	return (Order)this.getCurrentSession().load(Order.class, id);
    }
    
    /**
   	 *根据schoolID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Order> findOrderBySchoolID(long id){
    	return (List<Order>)this.getCurrentSession()
    			.createQuery("FROM Order o WHERE o.schoolID = :schoolID")
    			.setLong("schoolID", id).list();
    }
    
    /**
   	 *根据userID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Order> findOrderByUserID(long id){
    	return (List<Order>)this.getCurrentSession()
    			.createQuery("FROM Order o WHERE o.userID = :userID")
    			.setLong("userID", id).list();
    }
    
    /**
   	 *根据schoolID查找订单数量 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long findOrderCountBySchoolID(long schoolID){
    	return (Long)this.getCurrentSession()
    			.createQuery("SELECT count(id) FROM Order o WHERE o.schoolID = :schoolID")
    			.setLong("schoolID", schoolID).uniqueResult();
    }
    
    /**
   	 *得到全部订单Count
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Long findAllOrderCount(){
    	return (Long)this.getCurrentSession()
    			.createQuery("SELECT count(id) FROM Order")
    			.uniqueResult();
    }
    
    /**
   	 *根据contactName,contactMobile和orderNumber查找订单 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Order findOrderByMessage(String contactName,String contactMobile,int orderNumber){
    	return (Order)this.getCurrentSession()
    			.createQuery("FROM Order o WHERE o.contactName = :contactName and o.contactMobile = :contactMobile and o.orderNumber = :orderNumber")
    			.setString("contactName",contactName)
    			.setLong("contactMobile",Long.valueOf(contactMobile))
    			.setInteger("orderNumber", orderNumber)
    			.uniqueResult();
    }
    
    /**
   	 *得到全部订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Order> findAllOrder(){
    	return (List<Order>)this.getCurrentSession()
    			.createQuery("FROM Order")
    			.list();
    }
    
    /**
   	 *根据schoolID查找已经预约本驾校的学生(User)名单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Long> findUsersBySchoolID(long schoolID){
    	return (List<Long>)this.getCurrentSession()
    			.createQuery("SELECT DISTINCT userID FROM Order o WHERE o.schoolID = :schoolID AND o.status = 1")
    			.setLong("schoolID", schoolID).list();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
