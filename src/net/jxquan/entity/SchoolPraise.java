package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="schoolPraise")
public class SchoolPraise implements Serializable{
	private static final long serialVersionUID = 4430692725954228694L;
	@Id
	@GeneratedValue
	private long id;
	private long schoolID;
	@Override
	public String toString() {
		return "SchoolPraise [id=" + id + ", schoolID=" + schoolID
				+ ", praiseAmount=" + praiseAmount + ", totalStudent="
				+ totalStudent + ", ownStudent=" + ownStudent
				+ ", otherStudent=" + otherStudent + ", pariseRate="
				+ praiseRate + "]";
	}
	private int praiseAmount;
	private int totalStudent;
	private int ownStudent;
	private int otherStudent;
	private double praiseRate;
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
	public int getPraiseAmount() {
		return praiseAmount;
	}
	public void setPraiseAmount(int praiseAmount) {
		this.praiseAmount = praiseAmount;
	}
	public int getTotalStudent() {
		return totalStudent;
	}
	public void setTotalStudent(int totalStudent) {
		this.totalStudent = totalStudent;
	}
	public int getOwnStudent() {
		return ownStudent;
	}
	public void setOwnStudent(int ownStudent) {
		this.ownStudent = ownStudent;
	}
	public int getOtherStudent() {
		return otherStudent;
	}
	public void setOtherStudent(int otherStudent) {
		this.otherStudent = otherStudent;
	}
	public double getPariseRate() {
		return praiseRate;
	}
	public void setPariseRate(double praiseRate) {
		this.praiseRate = praiseRate;
	}
}

