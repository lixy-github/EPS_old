package com.broadcontact.clothing.entity.supermarket;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.impl.CriteriaImpl.OrderEntry;

import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;
/**
 * 评价类
 * @author chen
 */
@Entity
public class EvaluateSallerEntity extends BaseEntity{
	@ManyToOne
	private GoodsOrderEntity order; //交易订单
	
	@ManyToOne
	private UserEntity seller;  //卖家
	
	@ManyToOne
	private UserEntity buyer;  //买家
	
	private int starNum;
	
	private String pingjia; //评价
	
	private Date createTime;
	

	public EvaluateSallerEntity(GoodsOrderEntity order,int starNum ,String pingjia) {
		super();
		this.order = order;
		this.starNum = starNum;
		this.pingjia = pingjia;
		this.seller = order.getSeller();
		this.buyer = order.getUser();
		this.createTime = new Date();
	}
	public EvaluateSallerEntity() {
	}
	public UserEntity getSeller() {
		return seller;
	}

	public void setSeller(UserEntity seller) {
		this.seller = seller;
	}

	public UserEntity getBuyer() {
		return buyer;
	}

	public void setBuyer(UserEntity buyer) {
		this.buyer = buyer;
	}

	public String getPingjia() {
		return pingjia;
	}

	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public GoodsOrderEntity getOrder() {
		return order;
	}

	public void setOrder(GoodsOrderEntity order) {
		this.order = order;
	}
	public int getStarNum() {
		return starNum;
	}
	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}


}
