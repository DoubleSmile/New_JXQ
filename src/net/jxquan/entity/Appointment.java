package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="appointment")
public class Appointment implements Serializable{
	private static final long serialVersionUID = 4430692725954228694L;
	@Id
	@GeneratedValue
	private long id;
	private long userID;
	private long schoolID;
	private long coachID;
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", userID=" + userID + ", schoolID="
				+ schoolID + ", coachID=" + coachID + ", carID=" + carID
				+ ", appointNumber=" + appointNumber + ", acceptTime=" + acceptTime
				+ ", createTime=" + createTime + ", appointTime=" + appointTime
				+ ", timeInterval=" + timeInterval + ", contactName="
				+ contactName + ", contactMobile=" + contactMobile
				+ ", status=" + status + ", appointNumber=" + appointNumber
				+ "]";
	}
	private long carID;
	private int appointNumber;
	public int getAppointNumber() {
		return appointNumber;
	}
	public void setAppointNumber(int appointNumber) {
		this.appointNumber = appointNumber;
	}
	private Date acceptTime;
	private Date createTime;
	private Date appointTime;
	private int timeInterval;
	private String contactName;
	private long contactMobile;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(long schoolID) {
		this.schoolID = schoolID;
	}
	public long getCoachID() {
		return coachID;
	}
	public void setCoachID(long coachID) {
		this.coachID = coachID;
	}
	public long getCarID() {
		return carID;
	}
	public void setCarID(long carID) {
		this.carID = carID;
	}
	public Date getAcceptTime() {
		return acceptTime;
	}
	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(Date appointTime) {
		this.appointTime = appointTime;
	}
	public int getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public long getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(long contactMobile) {
		this.contactMobile = contactMobile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	private int status;
	
	
}

