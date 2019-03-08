package com.broadcontact.clothing.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.apache.struts2.json.annotations.JSON;

import com.broadcontact.common.entity.BaseEntity;

/**
 * 用户消息表
 * 用户审核不通过、群发等
 */
@Entity
public class UserNewsEntity extends BaseEntity {

	private String content;
	private Date time;
	private String type;//用户单位信息审核：1
						//工厂认证审核：2
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 
	@JoinTable(name = "user_userNews", 
		joinColumns ={@JoinColumn(name = "newId", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id")}
	)
	private Set<UserEntity> userSet = new HashSet<UserEntity>();

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	@JSON(serialize=false)
	public Set<UserEntity> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<UserEntity> userSet) {
		this.userSet = userSet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
