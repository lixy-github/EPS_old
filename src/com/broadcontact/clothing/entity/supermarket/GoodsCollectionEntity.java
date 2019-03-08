package com.broadcontact.clothing.entity.supermarket;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;
@Entity
public class GoodsCollectionEntity extends BaseEntity{
	@ManyToOne
	private UserEntity user;  //购买人
	
	@ManyToOne
	private GoodsEntity goods;  //收藏商品
	
	private Date createTime;	//收藏日期
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public GoodsEntity getGoods() {
		return goods;
	}

	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}
	
}
