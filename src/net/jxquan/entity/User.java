package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="user")
public class User implements Serializable{
	
	private static final long serialVersionUID = -6594407653493918062L;
	@Override
	public String toString() {
		return "User [id=" + id + ", mobile=" + mobile + ", password="
				+ password + ", weChat=" + weChat + ", isLocal=" + isLocal
				+ ", description=" + description + ", age=" + age + ", gender="
				+ gender + ", checkCode=" + checkCode + ", isAdmin=" + isAdmin
				+ ", address=" + address + "]";
	}
	@Id
	@GeneratedValue
	private long id;
	private long mobile;
	private String password;
	private String weChat;
	private int isLocal;
	private String description;
	private int age;
	private int gender;
	private String checkCode;
	private int isAdmin;
	private String address;
	private int valid;
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public int getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(int isLocal) {
		this.isLocal = isLocal;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
