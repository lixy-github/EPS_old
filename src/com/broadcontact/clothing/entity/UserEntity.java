package com.broadcontact.clothing.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.struts2.json.annotations.JSON;

import com.broadcontact.common.entity.BaseEntity;

@Entity
public class UserEntity extends BaseEntity {
	private String tel;//注册手机
	private String email;//邮箱 
	private String password;//密码
	private String username;//昵称
	private String headImg;//头像
	
	//完善信息
	private String companyName;//单位名称
	private String companyType;//单位类别 目前不考虑
	private String address;//地址
	private String nsrsbh;//纳税人识别号
	private String lxr;//联系人
	private String lxPhone;//联系电话
	private String advantage;//我能(公司优势) 1.生产订单 2.工序加工 3.毛衫设计 4.产品质检 5.原材料 6.成衣销售 7.仓储服务
	private String yyzzImg;//营业执照
	//private String swdjzImg;//营业执照下的第二张图片 名字自己设置
	//private String zzjgdmzImg;//营业执照下的第三张图片 名字自己设置
	//private String swdjzName;//第二张图片自己设置的名称
	//private String zzjgdmzName;//第三张图片自己设置的名称
	private String levels;//用户星级 1 2 3 4 5个级别
	private String userType;//用户类别 临时（null）----->状态1是已经审核用户(免费用户); 状态2是提交待审核  状态3 是审核不通过
	//private String remarks;//审核不通过的备注
	
	//特殊字段
	private String lostPwdTimes;//找回密码时 验证成功提交时间戳
	
	//关联
	//发布需求
	@OneToMany(mappedBy="userEntity",cascade=CascadeType.ALL)
	private Set<DemandEntity> demandSet = new HashSet<DemandEntity>();
	
	//需求匹配公司
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 
	@JoinTable(name = "company_demand", 
		joinColumns ={@JoinColumn(name = "userId", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "demandId", referencedColumnName = "id")}
	)
	private Set<DemandEntity> demandSet2 = new HashSet<DemandEntity>();
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 
	@JoinTable(name = "user_userNews", 
		joinColumns ={@JoinColumn(name = "userId", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "newId", referencedColumnName = "id")}
	)
	@OrderBy("id desc")
	private Set<UserNewsEntity> userNewsSet = new HashSet<UserNewsEntity>();
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JSON(serialize=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getLostPwdTimes() {
		return lostPwdTimes;
	}
	public void setLostPwdTimes(String lostPwdTimes) {
		this.lostPwdTimes = lostPwdTimes;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNsrsbh() {
		return nsrsbh;
	}
	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}
	public String getLxr() {
		return lxr;
	}
	public void setLxr(String lxr) {
		this.lxr = lxr;
	}
	public String getLxPhone() {
		return lxPhone;
	}
	public void setLxPhone(String lxPhone) {
		this.lxPhone = lxPhone;
	}
	public String getAdvantage() {
		return advantage;
	}
	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}
	public String getYyzzImg() {
		return yyzzImg;
	}
	public void setYyzzImg(String yyzzImg) {
		this.yyzzImg = yyzzImg;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Set<DemandEntity> getDemandSet() {
		return demandSet;
	}
	public void setDemandSet(Set<DemandEntity> demandSet) {
		this.demandSet = demandSet;
	}
	@JSON(serialize=false)
	public Set<UserNewsEntity> getUserNewsSet() {
		return userNewsSet;
	}
	public void setUserNewsSet(Set<UserNewsEntity> userNewsSet) {
		this.userNewsSet = userNewsSet;
	}
	@JSON(serialize=false)
	public Set<DemandEntity> getDemandSet2() {
		return demandSet2;
	}
	public void setDemandSet2(Set<DemandEntity> demandSet2) {
		this.demandSet2 = demandSet2;
	}
}
