package com.broadcontact.clothing.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.broadcontact.common.entity.BaseEntity;

@Entity
public class UserLoginIpEntity extends BaseEntity {

	private String ip;
	private Date time;
	private Long userId;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
