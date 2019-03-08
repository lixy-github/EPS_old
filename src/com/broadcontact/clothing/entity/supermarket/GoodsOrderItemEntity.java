package com.broadcontact.clothing.entity.supermarket;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.broadcontact.clothing.entity.ImageEntity;
import com.broadcontact.common.entity.BaseEntity;
@Entity
public class GoodsOrderItemEntity extends BaseEntity{
	@ManyToOne(cascade=CascadeType.MERGE,optional=false)
	private GoodsOrderEntity order;
    private String goodsRandom;	//商品Random  
	private String goodsType;	//商品类型
    private String goodsName;	//商品名称
    private Integer piece;	//件数
    private Double danjia; //单价
    private double yunfei; //运费
    private String remark; //备注
    private String imageUrl; //商品图片
    private String goodsStatus; //商品状态 退货
    private boolean isEnough;
    public GoodsOrderItemEntity(GoodsCartEntity cart) {
    	GoodsEntity goods = cart.getGoods();
		this.goodsRandom = goods.getRandom();
		this.goodsType = goods.getType();
		this.goodsName = goods.getName();
		this.piece = cart.getGoodsNum();
		this.danjia = goods.getDanjia();
		List<ImageEntity> imageList = goods.getImageList();
		if(imageList.size() !=0){
			this.imageUrl = goods.getImageList().get(0).getPath();
		}else{
			this.imageUrl = ImageEntity.DEFAULT_PATH;
		}
		
//		this.yunfei = goods.getWuliuMoney();
		
	}
    public GoodsOrderItemEntity() {
		
	}
	public GoodsOrderEntity getOrder() {
    	return order;
    }
    public void setOrder(GoodsOrderEntity order) {
    	this.order = order;
    }
    public String getGoodsRandom() {
    	return goodsRandom;
    }
    public void setGoodsRandom(String goodsRandom) {
    	this.goodsRandom = goodsRandom;
    }
    public String getGoodsType() {
    	return goodsType;
    }
    public void setGoodsType(String goodsType) {
    	this.goodsType = goodsType;
    }
    public String getGoodsName() {
    	return goodsName;
    }
    public void setGoodsName(String goodsName) {
    	this.goodsName = goodsName;
    }
    public Integer getPiece() {
    	return piece;
    }
    public void setPiece(Integer piece) {
    	this.piece = piece;
    }
    public Double getDanjia() {
    	return danjia;
    }
    public void setDanjia(Double danjia) {
    	this.danjia = danjia;
    }
    public String getRemark() {
    	return remark;
    }
    public void setRemark(String remark) {
    	this.remark = remark;
    }
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public double getYunfei() {
		return yunfei;
	}
	public void setYunfei(double yunfei) {
		this.yunfei = yunfei;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isEnough() {
		return isEnough;
	}
	public void setEnough(boolean isEnough) {
		this.isEnough = isEnough;
	}
	
}
