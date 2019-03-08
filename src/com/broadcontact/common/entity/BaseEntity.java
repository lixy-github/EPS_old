package com.broadcontact.common.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.RandomStringUtils;

@MappedSuperclass
public class BaseEntity implements Serializable{
	
	public static Long STATUS_DISABLED=new Long(0);
	public static Long STATUS_NORMAL=new Long(1);
	
	@Id
	@GeneratedValue
	private Long id;
	private String random;
	private Long status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	protected String createRandom(){
		return RandomStringUtils.randomAlphanumeric(15);
	}
	/**
	 * @hibernate.property
	 */
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) return false;
		if(obj instanceof BaseEntity) {
			if (obj.getClass().getName().equals(this.getClass().getName())) {
			BaseEntity o=(BaseEntity)obj;
			if((o.getId()!=null)&&(this.id!=null)&&(o.getId().longValue()==this.id.longValue()))
				return true;
			else
				if ((o.getId()==null)&&(this.id==null))
					return super.equals(obj);
				else
					return false;
			} else return false;
		}
		return super.equals(obj);
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
  
}
