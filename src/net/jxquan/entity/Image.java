package net.jxquan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="image")
public class Image implements Serializable{
	
	private static final long serialVersionUID = -1226064686786127832L;

	private long id;
	
	private String key;
	private String url;
	@Override
	public String toString() {
		return "Image [id=" + id + ", key=" + key + ", url=" + url + "]";
	}
	@Column(name="urlKey")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
}

