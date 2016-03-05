package net.jxquan.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="examFour")
public class ExamFour implements Serializable{

	@Override
	public String toString() {
		return "ExamFour [id=" + id + ", title=" + title + ", imageUrl="
				+ imageUrl + ", A=" + A + ", B=" + B + ", C=" + C + ", D=" + D
				+ ", answer=" + answer + ", explain=" + explain + ", examType="
				+ examType + ", difficulty=" + difficulty + "]";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -9065599312773213135L;
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String imageUrl;
	private String A;
	private String B;
	private String C;
	private String D;
	private String answer;
	private String explain;
	private int examType;
	private int difficulty;
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public int getExamType() {
		return examType;
	}
	public void setExamType(int examType) {
		this.examType = examType;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
