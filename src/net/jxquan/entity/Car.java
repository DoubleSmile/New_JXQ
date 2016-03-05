package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="car")
public class Car implements Serializable{
	
	private static final long serialVersionUID = -6594407653493918062L;
	@Id
	@GeneratedValue
	private long id;
	private long schoolID;
	private String number;
	private int maxSize;
//	private String imageUrl;
	private long coachID;
	public long getCoachID() {
		return coachID;
	}
	public void setCoachID(long coachID) {
		this.coachID = coachID;
	}
//	public String getImageUrl() {
//		return imageUrl;
//	}
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public int getCurSize() {
		return curSize;
	}
	public void setCurSize(int curSize) {
		this.curSize = curSize;
	}
	private int curSize;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSchoolID() {
		return schoolID;
	}
	public void setSchoolID(long schoolID) {
		this.schoolID = schoolID;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}