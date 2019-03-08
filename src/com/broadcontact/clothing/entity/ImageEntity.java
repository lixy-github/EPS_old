package com.broadcontact.clothing.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.broadcontact.clothing.entity.supermarket.GoodsEntity;
import com.broadcontact.clothing.entity.supermarket.EvaluateEntity;
import com.broadcontact.common.entity.BaseEntity;

/**
 * 需求
 */
@Entity
public class ImageEntity extends BaseEntity {
	public static String DEFAULT_PATH = "/images/s-3-in-one-lux-router-550x550.jpg";
	private String path;
	private String smallPath;
	@ManyToOne()
	private GoodsEntity goods;
	
	@ManyToOne()
	private EvaluateEntity userEvaluate;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public GoodsEntity getGoods() {
		return goods;
	}
	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}
	public String getSmallPath() {
		return smallPath;
	}
	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}
	public EvaluateEntity getUserEvaluate() {
		return userEvaluate;
	}
	public void setUserEvaluate(EvaluateEntity userEvaluate) {
		this.userEvaluate = userEvaluate;
	}
}
