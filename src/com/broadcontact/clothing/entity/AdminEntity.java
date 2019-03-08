package com.broadcontact.clothing.entity;

import javax.persistence.Entity;

import com.broadcontact.common.entity.BaseEntity;

/**
 * 管理员账号
 */
@Entity
public class AdminEntity extends BaseEntity{

	private String tel;
	private String password;
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
