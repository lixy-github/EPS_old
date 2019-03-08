package com.broadcontact.clothing.entity.supermarket;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;
@Entity
public class GoodsCartEntity extends BaseEntity{
	@ManyToOne
	private UserEntity user;  //购买人
	
	private boolean isCheck; //是否选中
	
	@ManyToOne
	private GoodsEntity goods;	//商品
	
	private int goodsNum;  //数量
	
	public GoodsCartEntity(){}
	public GoodsCartEntity(UserEntity user, GoodsEntity goods, int goodsNum) {
		super();
		super.setRandom(createRandom());
		this.user = user;
		this.goods = goods;
		this.goodsNum = goodsNum;
		this.isCheck = true;
	}
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public GoodsEntity getGoods() {
		return goods;
	}

	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}

	public int getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	
}
