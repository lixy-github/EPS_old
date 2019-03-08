package com.broadcontact.clothing.entity.supermarket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.broadcontact.common.entity.BaseEntity;
@Entity
public class HTMLEntity extends BaseEntity{
	@OneToOne
	private GoodsEntity goods;
	@Lob()
	@Column(columnDefinition = "MEDIUMTEXT", nullable = true)
	private String content; // 商品详情	
	
	public GoodsEntity getGoods() {
		return goods;
	}
	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
