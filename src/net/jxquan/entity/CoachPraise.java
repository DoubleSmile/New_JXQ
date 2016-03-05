package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="coachPraise")
public class CoachPraise implements Serializable{
	private static final long serialVersionUID = 4430692725954228694L;
	@Id
	@GeneratedValue
	private long id;
	@Override
	public String toString() {
		return "CoachPraise [id=" + id + ", coachID=" + coachID
				+ ", praiseAmount=" + praiseAmount + ", totalStudent="
				+ totalStudent + ", ownStudent=" + ownStudent
				+ ", otherStudent=" + otherStudent + ", pariseRate="
				+ pariseRate + "]";
	}
	private long coachID;
	private int praiseAmount;
	private int totalStudent;
	private int ownStudent;
	private int otherStudent;
	private double pariseRate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
		return pariseRate;
	}
	public void setPariseRate(double pariseRate) {
		this.pariseRate = pariseRate;
	}
	public long getCoachID() {
		return coachID;
	}
	public void setCoachID(long coachID) {
		this.coachID = coachID;
	}
}

