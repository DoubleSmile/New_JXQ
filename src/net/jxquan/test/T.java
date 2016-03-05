package net.jxquan.test;

public class T {
	private long id;
	private String name;
	private String password;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "T [id=" + id + ", name=" + name + ", password=" + password
				+ "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public T() {
		super();
	}
	public T(long id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}
}
