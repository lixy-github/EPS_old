package com.broadcontact.clothing.entity;

import javax.persistence.Entity;

import com.broadcontact.common.entity.BaseEntity;

/**
 *	工厂 认证
 */
@Entity
public class ManufacturerEntity extends BaseEntity{

	//status:null 初始状态
	//status:1 审核通过
	//status:2 提交待审核
	//status:3 审核不通过
	public static Long STATUS_PASS = new Long(1);
	public static Long STATUS_WAIT = new Long(2);
	public static Long STATUS_FAILED= new Long(3);
	
	private String tel;//联系电话
	private String lxr;//联系人
	private String address;//地址
	private Long userId;
	
	//管理员需要填写的工厂数据
	private String jqsbsl;//机器设备数量
	private String grsl;//工人数量
	private String pkgldj;//品控管理等级 A B C D E
	private String zdrcl;//最大日产量
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getJqsbsl() {
		return jqsbsl;
	}
	public void setJqsbsl(String jqsbsl) {
		this.jqsbsl = jqsbsl;
	}
	public String getGrsl() {
		return grsl;
	}
	public void setGrsl(String grsl) {
		this.grsl = grsl;
	}
	public String getPkgldj() {
		return pkgldj;
	}
	public void setPkgldj(String pkgldj) {
		this.pkgldj = pkgldj;
	}
	public String getZdrcl() {
		return zdrcl;
	}
	public void setZdrcl(String zdrcl) {
		this.zdrcl = zdrcl;
	}
	
}
