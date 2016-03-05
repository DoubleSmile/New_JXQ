package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="serveRelation")
public class ServeRelation implements Serializable{
	private static final long serialVersionUID = 4430692725954228694L;
	@Id
	@GeneratedValue
	private long id;
	private long schoolID;
	private String college;
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public long getId() {
		return id;
	}
	@Override
	public String toString() {
		return "ServeRelation [id=" + id + ", schoolID=" + schoolID
				+ ", college=" + college + "]";
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
}

