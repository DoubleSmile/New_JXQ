package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="orders")
public class Order implements Serializable{
	private static final long serialVersionUID = 4430692725954228694L;
	@Id
	@GeneratedValue
	private long id;
	private long userID;
	private long schoolID;
	private int orderNumber;
	private Date acceptTime;
	private Date createTime;
	private Date cancelTime;
	private int isLocal;
	private String contactName;
	private long contactMobile;
	private int status;
	@Override
	public String toString() {
		return "Order [id=" + id + ", userID=" + userID + ", schoolID="
				+ schoolID + ", acceptTime=" + acceptTime + ", orderNumber="
				+ orderNumber + ", createTime=" + createTime + ", cancelTime="
				+ cancelTime + ", isLocal=" + isLocal + ", contactName="
				+ contactName + ", contactMobile=" + contactMobile
				+ ", status=" + status + "]";
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
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
	public Date getCancelTime() {
		return cancelTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
    public int getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(int isLocal) {
		this.isLocal = isLocal;
	}
	
}

