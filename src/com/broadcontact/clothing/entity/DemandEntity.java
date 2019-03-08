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
import javax.persistence.ManyToOne;

import org.apache.struts2.json.annotations.JSON;

import com.broadcontact.common.entity.BaseEntity;

/**
 * 需求
 */
@Entity
public class DemandEntity extends BaseEntity {
	
	//status:null 初始状态 用户创建需求后的状态
	//status:1 admin管理员处理后的 即 匹配好公司的
	public static Long STATUS_HANDLE = new Long(1);

	private String demandType;//需求类型 1.成衣订购 2.工序加工 3.原料采购 4.服装设计
	private String demandTypeChildren;//工序加工 织片 套口缝盘 整烫 包装 印花
	private String ggyq;//规格要求
	private String slyq;//数量要求 
	private String sjyq;//时间要求
	private String moneyStart;//价格区间
	private String moneyEnd;
	private String memo;//详细描述
	private Date time;//发布时间
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserEntity userEntity;//发布者
	
	//需求匹配公司
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 
	@JoinTable(name = "company_demand", 
		joinColumns ={@JoinColumn(name = "demandId", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id")}
	)
	private Set<UserEntity> companySet = new HashSet<UserEntity>();

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getDemandTypeChildren() {
		return demandTypeChildren;
	}

	public void setDemandTypeChildren(String demandTypeChildren) {
		this.demandTypeChildren = demandTypeChildren;
	}

	public String getGgyq() {
		return ggyq;
	}

	public void setGgyq(String ggyq) {
		this.ggyq = ggyq;
	}

	public String getSlyq() {
		return slyq;
	}

	public void setSlyq(String slyq) {
		this.slyq = slyq;
	}

	public String getSjyq() {
		return sjyq;
	}

	public void setSjyq(String sjyq) {
		this.sjyq = sjyq;
	}

	public String getMoneyStart() {
		return moneyStart;
	}

	public void setMoneyStart(String moneyStart) {
		this.moneyStart = moneyStart;
	}

	public String getMoneyEnd() {
		return moneyEnd;
	}

	public void setMoneyEnd(String moneyEnd) {
		this.moneyEnd = moneyEnd;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@JSON(serialize=false)
	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@JSON(serialize=false)
	public Set<UserEntity> getCompanySet() {
		return companySet;
	}

	public void setCompanySet(Set<UserEntity> companySet) {
		this.companySet = companySet;
	}
	
	
}
