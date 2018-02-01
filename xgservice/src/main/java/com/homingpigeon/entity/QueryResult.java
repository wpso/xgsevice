package com.homingpigeon.entity;

public class QueryResult {
	private String pid;
	private String tid;
	private String name;
	private String cage;
	private String location;
	private int p_status;
	private int t_status;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCage() {
		return cage;
	}
	public void setCage(String cage) {
		this.cage = cage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getP_status() {
		return p_status;
	}
	public void setP_status(int p_status) {
		this.p_status = p_status;
	}
	public int getT_status() {
		return t_status;
	}
	public void setT_status(int t_status) {
		this.t_status = t_status;
	}
	@Override
	public String toString() {
		return "QueryResult [pid=" + pid + ", tid=" + tid + ", name=" + name
				+ ", cage=" + cage + ", location=" + location + ", p_status="
				+ p_status + ", t_status=" + t_status + "]";
	}
	
	
}
