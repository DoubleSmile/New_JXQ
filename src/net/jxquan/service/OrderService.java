package net.jxquan.service;

import java.util.Date;
import java.util.List;

import net.jxquan.dao.OrderDAO;
import net.jxquan.entity.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
   private OrderDAO orderDAO;
 
	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
	@Autowired
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	@CacheEvict(value="orderCache",allEntries=true)
	public void addOrder(Order order){
		orderDAO.addOrder(order);
	}
	
	@CacheEvict(value="orderCache",allEntries=true)
	public void deleteOrder(long orderID){
		orderDAO.deleteOrder(orderID);
	}
	
	@CacheEvict(value="orderCache",allEntries=true)
	public void acceptOrder(String contactName,String contactMobile,int orderNumber){
		Order order=orderDAO.findOrderByMessage(contactName, contactMobile, orderNumber);
		order.setAcceptTime(new Date());
		order.setStatus(1);
		orderDAO.updateOrder(order);
	}
	
	@Cacheable(value="orderCache",key="#root.methodName+#schoolID")
	public List<Order> getOrderForSchool(long schoolID){
		return orderDAO.findOrderBySchoolID(schoolID);
	}
	@Cacheable(value="orderCache",key="#root.methodName+#userID")
	public List<Order> getOrderByUserID(long userID){
		return orderDAO.findOrderByUserID(userID);
	}
	@Cacheable(value="orderCache",key="#root.methodName+#schoolID")
	public long getOrderCountBySchoolID(long schoolID){
		return orderDAO.findOrderCountBySchoolID(schoolID);
	}
	
//	@Cacheable(value="orderCache",key="#root.methodName+#schoolID")
	public long getAllOrderCount(){
		return orderDAO.findAllOrderCount();
	}
	
	public List<Long> getUsersBySchoolID(long schoolID){
		return orderDAO.findUsersBySchoolID(schoolID);
	}
}
