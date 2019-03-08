package com.broadcontact.clothing.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.struts2.json.annotations.JSON;

import com.broadcontact.common.entity.BaseEntity;

@Entity
public class AddressEntity extends BaseEntity {
	@ManyToOne
	private UserEntity user;
	
	private String linkMan; // 联系人
	private String phoneSms; // 手机
	private String province; // 省份
	private String city; // 城市
	private String county; // 区县
	private String detalAddress; // 详细地址
	private Date createTime;	//创建时间  默认收货地址时间最大
	public AddressEntity(){}
	public AddressEntity(UserEntity user, String linkMan, String phoneSms,
			String province, String city, String county,String detalAddress) {
		super();
		this.user = user;
		this.linkMan = linkMan;
		this.phoneSms = phoneSms;
		this.setDetalAddress(detalAddress);
		this.province = province;
		this.city = city;
		this.county = county;
	}
	@JSON(serialize=false)
	public UserEntity getUser() {
		return user;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getPhoneSms() {
		return phoneSms;
	}
	public void setPhoneSms(String phoneSms) {
		this.phoneSms = phoneSms;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detalAddress== null) ? 0 : detalAddress.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((linkMan == null) ? 0 : linkMan.hashCode());
		result = prime * result
				+ ((phoneSms == null) ? 0 : phoneSms.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		if (getDetalAddress() == null) {
			if (other.getDetalAddress() != null)
				return false;
		} else if (!getDetalAddress().equals(other.getDetalAddress()))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (linkMan == null) {
			if (other.linkMan != null)
				return false;
		} else if (!linkMan.equals(other.linkMan))
			return false;
		if (phoneSms == null) {
			if (other.phoneSms != null)
				return false;
		} else if (!phoneSms.equals(other.phoneSms))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDetalAddress() {
		return detalAddress;
	}
	public void setDetalAddress(String detalAddress) {
		this.detalAddress = detalAddress;
	}
	
}
