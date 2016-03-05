package net.jxquan.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class School implements Serializable{

	private static final long serialVersionUID = 7452869631985352694L;
	
	@Override
	public String toString() {
		return "School [id=" + id + ", schoolName=" + schoolName
				+ ", password=" + password + ", mobile=" + mobile
				+ ", telephone=" + telephone + ", mail=" + mail + ", address="
				+ address + ", isMaster=" + isMaster + ", teacherResource="
				+ teacherResource + ", localPrice=" + localPrice
				+ ", foreignPrice=" + foreignPrice + ", otherPrice="
				+ otherPrice + ", studentPerCar=" + studentPerCar
				+ ", discountExpire=" + discountExpire + ", hardwareResource="
				+ hardwareResource + ", durationOne=" + durationOne
				+ ", durationTwo=" + durationTwo + ", durationThree="
				+ durationThree + ", durationFour=" + durationFour
				+ ", passRateOne=" + passRateOne + ", passRateTwo="
				+ passRateTwo + ", passRateThree=" + passRateThree
				+ ", passRateFour=" + passRateFour + ", cookie=" + cookie
				+ ", status=" + status + ", discount=" + discount + "]";
	}
	private long id;
	private String schoolName;
	private String password;
	private long mobile;
	private long telephone;
	private String mail;
	private String address;
	private int isMaster;
	private String teacherResource;
	private double localPrice;
	private double foreignPrice;
	private double otherPrice;
	private int studentPerCar;
	private Date discountExpire;
	private String hardwareResource;
	private int durationOne;
	private int durationTwo;
	private int durationThree;
	private int durationFour;
	private double passRateOne;
	private double passRateTwo;
	private double passRateThree;
	private double passRateFour;
	private String cookie;
	private int status;
	private int discount;
	private String location;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getTeacherResource() {
		return teacherResource;
	}
	public void setTeacherResource(String teacherResource) {
		this.teacherResource = teacherResource;
	}
	public double getLocalPrice() {
		return localPrice;
	}
	public void setLocalPrice(double localPrice) {
		this.localPrice = localPrice;
	}
	public double getForeignPrice() {
		return foreignPrice;
	}
	public void setForeignPrice(double foreignPrice) {
		this.foreignPrice = foreignPrice;
	}
	public double getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(double otherPrice) {
		this.otherPrice = otherPrice;
	}
	public int getStudentPerCar() {
		return studentPerCar;
	}
	public void setStudentPerCar(int studentPerCar) {
		this.studentPerCar = studentPerCar;
	}
	public Date getDiscountExpire() {
		return discountExpire;
	}
	public void setDiscountExpire(Date discountExpire) {
		this.discountExpire = discountExpire;
	}
	public String getHardwareResource() {
		return hardwareResource;
	}
	public void setHardwareResource(String hardwareResource) {
		this.hardwareResource = hardwareResource;
	}
	public int getDurationOne() {
		return durationOne;
	}
	public void setDurationOne(int durationOne) {
		this.durationOne = durationOne;
	}
	public int getDurationTwo() {
		return durationTwo;
	}
	public void setDurationTwo(int durationTwo) {
		this.durationTwo = durationTwo;
	}
	public int getDurationThree() {
		return durationThree;
	}
	public void setDurationThree(int durationThree) {
		this.durationThree = durationThree;
	}
	public int getDurationFour() {
		return durationFour;
	}
	public void setDurationFour(int durationFour) {
		this.durationFour = durationFour;
	}
	public double getPassRateOne() {
		return passRateOne;
	}
	public void setPassRateOne(double passRateOne) {
		this.passRateOne = passRateOne;
	}
	public double getPassRateTwo() {
		return passRateTwo;
	}
	public void setPassRateTwo(double passRateTwo) {
		this.passRateTwo = passRateTwo;
	}
	public double getPassRateThree() {
		return passRateThree;
	}
	public void setPassRateThree(double passRateThree) {
		this.passRateThree = passRateThree;
	}
	public double getPassRateFour() {
		return passRateFour;
	}
	public void setPassRateFour(double passRateFour) {
		this.passRateFour = passRateFour;
	}
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public long getTelephone() {
		return telephone;
	}
	public void setTelephone(long telephone) {
		this.telephone = telephone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getIsMaster() {
		return isMaster;
	}
	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}
}
