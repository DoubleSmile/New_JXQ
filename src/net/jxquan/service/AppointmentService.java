package net.jxquan.service;

import java.util.Date;
import java.util.List;

import net.jxquan.dao.AppointmentDAO;
import net.jxquan.entity.Appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="appointmentService")
public class AppointmentService {
	private AppointmentDAO appointmentDAO;

	public AppointmentDAO getAppointmentDAO() {
		return appointmentDAO;
	}
	
    @Autowired
	public void setappointmentDAO(AppointmentDAO appointmentDAO) {
		this.appointmentDAO = appointmentDAO;
	}
    
    @Cacheable(value="appointmentCache",key="#root.methodName")
    public List<Appointment> getAllappointments(){
    	return appointmentDAO.findAllAppointment();
    }
    
    @CacheEvict(value="appointmentCache",allEntries=true)
    public void addAppointment(Appointment appointment){
    	this.getAppointmentDAO().addAppointment(appointment);
    }
    
    @CacheEvict(value="appointmentCache",allEntries=true)
	public void deleteAppointment(long appointmentID){
    	this.getAppointmentDAO().deleteAppointment(appointmentID);
	}
    
    @CacheEvict(value="appointmentCache",allEntries=true)
	public void acceptAppointment(String contactName,String contactMobile,int AppointmentNumber){
		Appointment appointment=appointmentDAO.findAppointmentByMessage(contactName, contactMobile, AppointmentNumber);
		appointment.setAcceptTime(new Date());
		appointment.setStatus(1);
		this.getAppointmentDAO().updateAppointment(appointment);
	}
    
    @Cacheable(value="appointmentCache",key="#root.methodName+#schoolID")
	public List<Appointment> getAppointmentForSchool(long schoolID){
		return appointmentDAO.findAppointmentBySchoolID(schoolID);
	}
    
    @Cacheable(value="appointmentCache",key="#root.methodName+#userID")
	public List<Appointment> getAppointmentForUser(long userID){
		return appointmentDAO.findAppointmentByUserID(userID);
	}
    
}
