package com.broadcontact.clothing.entity.supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.broadcontact.clothing.entity.AddressEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;

@Entity
public class GoodsOrderEntity extends BaseEntity {
	public static Long ORDERSTATUS_YQX = new Long(-1); // 已取消
	public static Long ORDERSTATUS_WZF = new Long(0); // 未支付
	public static Long ORDERSTATUS_YZF = new Long(10); // 已支付 待发货
	public static Long ORDERSTATUS_YFH = new Long(20); // 已发货 待签收
	public static Long ORDERSTATUS_YWC = new Long(100); // 已完成
	
	@ManyToOne
	private UserEntity user; // 购买人
	
	@ManyToOne
	private UserEntity seller; //卖家
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<GoodsOrderItemEntity> items = new ArrayList<GoodsOrderItemEntity>();	//商品信息
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<GoodsOrderEntity> children = new ArrayList<GoodsOrderEntity>();
	
	@ManyToOne
	private GoodsOrderEntity parent;
	

	private String orderNumber; // 订单号
	private int orderStatus; // 订单状态
	private String payMethod; // 支付方式
	private String expressNumber;	//快递单号
	
	private double totalMoney;	//总金额
	private double totalYunfei; //总运费
	private int totalNum;	//商品数
	
	private String sendLinkMan; // 寄件联系人
	private String sendPhoneSms; // 寄件手机
	private String sendDetailAddress; // 寄件地址
	private String sendProvince; // 寄件省份
	private String sendCity; // 寄件城市
	private String sendCounty; // 寄件区县

	private String receiveLinkMan; // 收件联系人
	private String receivePhoneSms; // 收件手机
	private String receiveDetailAddress; // 收件地址
	private String receiveProvince; // 收件省份
	private String receiveCity; // 收件城市
	private String receiveCounty; // 收件区县

	private Date createTime; // 创建时间
	private Date payTime; // 付款时间
	private String payType;	//付款方式
	private Date sendTime; // 发货时间
	
	private boolean isUserEvaluateOver;  //买家是否评价过
	private boolean isSellerEvaluateOver;  //卖家是否评价过
	
	private String cancelReason;	//取消订单原因
	public GoodsOrderEntity() {}
	
	public GoodsOrderEntity(UserEntity purchaser) {
		super.setRandom(createRandom());
		this.orderNumber = createOrderNumber();
		this.user = purchaser;
		this.createTime = new Date();
	}
	public GoodsOrderEntity(UserEntity purchaser,UserEntity seller) {
		super.setRandom(createRandom());
		this.orderNumber = createOrderNumber();
		this.user = purchaser;
		this.seller = seller;
		this.createTime = new Date();
	}
	
	public void setReceiveAddress(AddressEntity receiveAddress){
		this.receiveCounty = receiveAddress.getCounty();
		this.receiveLinkMan = receiveAddress.getLinkMan();
		this.receivePhoneSms = receiveAddress.getPhoneSms();
		this.receiveDetailAddress = receiveAddress.getDetalAddress();
		this.receiveProvince = receiveAddress.getProvince();
		this.receiveCity = receiveAddress.getCity();
		this.receiveCounty = receiveAddress.getCounty();
	}
	
	public String getReceiveLinkMan() {
		return receiveLinkMan;
	}

	public void setReceiveLinkMan(String receiveLinkMan) {
		this.receiveLinkMan = receiveLinkMan;
	}

	public String getReceivePhoneSms() {
		return receivePhoneSms;
	}

	public void setReceivePhoneSms(String receivePhoneSms) {
		this.receivePhoneSms = receivePhoneSms;
	}

	public String getReceiveProvince() {
		return receiveProvince;
	}

	public void setReceiveProvince(String receiveProvince) {
		this.receiveProvince = receiveProvince;
	}

	public String getReceiveCity() {
		return receiveCity;
	}

	public void setReceiveCity(String receiveCity) {
		this.receiveCity = receiveCity;
	}

	public String getReceiveCounty() {
		return receiveCounty;
	}

	public void setReceiveCounty(String receiveCounty) {
		this.receiveCounty = receiveCounty;
	}

	public static String createOrderNumber() {
		return new Date().getTime() + "" + Thread.currentThread().getId();
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<GoodsOrderItemEntity> getItems() {
		return items;
	}

	public void setItems(List<GoodsOrderItemEntity> items) {
		this.items = items;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getSendLinkMan() {
		return sendLinkMan;
	}

	public void setSendLinkMan(String sendLinkMan) {
		this.sendLinkMan = sendLinkMan;
	}

	public String getSendPhoneSms() {
		return sendPhoneSms;
	}

	public void setSendPhoneSms(String sendPhoneSms) {
		this.sendPhoneSms = sendPhoneSms;
	}


	public String getSendProvince() {
		return sendProvince;
	}

	public void setSendProvince(String sendProvince) {
		this.sendProvince = sendProvince;
	}

	public String getSendCity() {
		return sendCity;
	}

	public void setSendCity(String sendCity) {
		this.sendCity = sendCity;
	}

	public String getSendCounty() {
		return sendCounty;
	}

	public void setSendCounty(String sendCounty) {
		this.sendCounty = sendCounty;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public List<GoodsOrderEntity> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsOrderEntity> children) {
		this.children = children;
	}

	public GoodsOrderEntity getParent() {
		return parent;
	}

	public void setParent(GoodsOrderEntity parent) {
		this.parent = parent;
	}

	public UserEntity getSeller() {
		return seller;
	}

	public void setSeller(UserEntity seller) {
		this.seller = seller;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getTotalYunfei() {
		return totalYunfei;
	}

	public void setTotalYunfei(double totalYunfei) {
		this.totalYunfei = totalYunfei;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	@Override
	public String toString() {
		return "GoodsOrderEntity [orderNumber=" + orderNumber
				+ ", orderStatus=" + orderStatus + ", totalMoney=" + totalMoney
				+ ", totalYunfei=" + totalYunfei + ", totalNum=" + totalNum
				+ ", sendLinkMan=" + sendLinkMan + ", sendPhoneSms="
				+ sendPhoneSms + ", sendDetailAddress=" + sendDetailAddress
				+ ", sendProvince=" + sendProvince + ", sendCity=" + sendCity
				+ ", sendCounty=" + sendCounty + ", receiveLinkMan="
				+ receiveLinkMan + ", receivePhoneSms=" + receivePhoneSms
				+ ", receiveDetailAddress=" + receiveDetailAddress + ", receiveProvince="
				+ receiveProvince + ", receiveCity=" + receiveCity
				+ ", receiveCounty=" + receiveCounty + ", createTime="
				+ createTime + ", payTime=" + payTime + ", sendTime="
				+ sendTime + "]";
	}

	public String getSendDetailAddress() {
		return sendDetailAddress;
	}

	public void setSendDetailAddress(String sendDetailAddress) {
		this.sendDetailAddress = sendDetailAddress;
	}

	public String getReceiveDetailAddress() {
		return receiveDetailAddress;
	}

	public void setReceiveDetailAddress(String receiveDetailAddress) {
		this.receiveDetailAddress = receiveDetailAddress;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public boolean isUserEvaluateOver() {
		return isUserEvaluateOver;
	}

	public void setUserEvaluateOver(boolean isUserEvaluateOver) {
		this.isUserEvaluateOver = isUserEvaluateOver;
	}

	public boolean isSellerEvaluateOver() {
		return isSellerEvaluateOver;
	}

	public void setSellerEvaluateOver(boolean isSellerEvaluateOver) {
		this.isSellerEvaluateOver = isSellerEvaluateOver;
	}


}
