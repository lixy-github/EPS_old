package com.broadcontact.common.entity;

import javax.persistence.Entity;

/**
 * @author Mao Yingzhou
 * @hibernate.class
 */
@Entity
public class SequenceEntity extends BaseEntity {

	private String keyName;		//序列名
	private Long keyValue;		//序列号

	/**
	 * @hibernate.property
	 */
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * @hibernate.property
	 */
	public Long getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(Long keyValue) {
		this.keyValue = keyValue;
	}
}
