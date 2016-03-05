package net.jxquan.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="examFourInfo")
public class ExamFourInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9065599312773213135L;
	@Id
	@GeneratedValue
	private long id;
	
	private long userID;
	
	@Override
	public String toString() {
		return "ExamOneInfo [id=" + id + ", userID=" + userID + ", lastExam="
				+ lastExam + ", wrongExam=" + wrongExam + "]";
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

	public int getLastExam() {
		return lastExam;
	}

	public void setLastExam(int lastExam) {
		this.lastExam = lastExam;
	}

	public String getWrongExam() {
		return wrongExam;
	}

	public void setWrongExam(String wrongExam) {
		this.wrongExam = wrongExam;
	}

	private int lastExam;
	
	private String wrongExam;
	
}
