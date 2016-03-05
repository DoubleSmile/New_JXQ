package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.Appointment;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="appointmentDAO")
public class AppointmentDAO {
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
	 * 添加约车信息
	 */
    @Transactional
    public void addAppointment(Appointment appointment){
    	this.getCurrentSession().save(appointment);
    }
    
    /**
	 *删除约车订单
	 */
    @Transactional
    public void deleteAppointment(Appointment appointment){
    	this.getCurrentSession().delete(appointment);
    }
    
    /**
	 *根据ID删除Appointment
	 */
    @Transactional
    public void deleteAppointment(long id){
    	Appointment appointment=(Appointment)this.getCurrentSession().get(Appointment.class,id);
        this.getCurrentSession().delete(appointment);
    }
    
    /**
   	 *更新订单 
   	 */
    @Transactional
    public void updateAppointment(Appointment appointment){
    	Appointment tempAppointment=(Appointment)this.getCurrentSession().get(Appointment.class, appointment.getId());
    	CloneUtils.clone(tempAppointment, appointment);
    	this.getCurrentSession().saveOrUpdate(tempAppointment);
    }
    
    /**
   	 *根据ID查找订单 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Appointment findAppointmentByID(long id){
    	return (Appointment)this.getCurrentSession().get(Appointment.class, id);
    }
    
    /**
   	 *根据schoolID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Appointment> findAppointmentBySchoolID(long id){
    	return (List<Appointment>)this.getCurrentSession()
    			.createQuery("FROM Appointment o WHERE o.schoolID = :schoolID")
    			.setLong("schoolID", id)
    			.list();
    }
    
    /**
   	 *根据userID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Appointment> findAppointmentByUserID(long id){
    	return (List<Appointment>)this.getCurrentSession()
    			.createQuery("FROM Appointment o WHERE o.userID = :userID")
    			.setLong("userID", id).list();
    }
    
    /**
   	 *根据coachID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Appointment> findAppointmentByCoachID(long id){
    	return (List<Appointment>)this.getCurrentSession()
    			.createQuery("FROM Appointment o WHERE o.coachID = :coachID")
    			.setLong("coachID", id).list();
    }
    
    /**
   	 *根据carID查找订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Appointment> findAppointmentByCarID(long id){
    	return (List<Appointment>)this.getCurrentSession()
    			.createQuery("FROM Appointment o WHERE o.carID = :carID")
    			.setLong("carID", id)
    			.list();
    }
    
    /**
   	 *根据schoolID查找订单数量 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long findAppointmentCountBySchoolID(long schoolID){
    	String sql="SELECT count(id) FROM Appointment a WHERE a.schoolID = :schoolID";
    	return (Long)this.getCurrentSession()
    			.createQuery(sql)
    			.setLong("schoolID", schoolID)
    			.uniqueResult();
    }
    
    
    
    /**
   	 *根据contactName,contactMobile和AppointmentNumber查找订单 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Appointment findAppointmentByMessage(String contactName,String contactMobile,int appointNumber){
    	return (Appointment)this.getCurrentSession()
    			.createQuery("FROM Appointment o WHERE o.contactName = :contactName and o.contactMobile = :contactMobile and o.appointNumber = :appointNumber")
    			.setString("contactName",contactName)
    			.setLong("contactMobile",Long.valueOf(contactMobile))
    			.setInteger("appointNumber", appointNumber)
    			.uniqueResult();
    }
    
    /**
   	 *得到全部订单 
   	 */
    @SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Appointment> findAllAppointment(){
    	return (List<Appointment>)this.getCurrentSession()
    			.createQuery("FROM Appointment")
    			.list();
    }
    
    /**
   	 *根据schoolID和coachID查找订单数量
   	 *其实是为了得到相关教练的被预约次数(销量) 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public long findAppointmentCountByCoachID(long schoolID,long coachID){
    	String sql="SELECT count(id) FROM Appointment a WHERE a.schoolID = :schoolID AND a.coachID = :coachID";
    	return (Long)this.getCurrentSession()
    			.createQuery(sql)
    			.setLong("schoolID", schoolID)
    			.setLong("coachID",coachID)
    			.uniqueResult();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
