package com.broadcontact.clothing.entity.supermarket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.broadcontact.clothing.entity.ImageEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;
/**
 * 评价类
 * @author chen
 */
@Entity
public class EvaluateEntity extends BaseEntity{
	@ManyToOne
	private GoodsEntity goods; //交易商品	
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userEvaluate")
	private List<ImageEntity> imageList = new ArrayList<ImageEntity>(); // 评价图片
	
	@ManyToOne
	private UserEntity user;  //购买人
	
	private String pingjia; //评价
	private int starSP; //商品描述
	private int starWL;	//物流服务
	private int starFW;	//服务态度
	private Date dateTimePJ;	//评价时间
	private Date dateTimeJY;	//交易时间
	
	private boolean isInvisible; //是否匿名
	
	public EvaluateEntity() {	}
	public EvaluateEntity(UserEntity user,GoodsEntity goods, String pingjia, int starSP,
			int starWL, int starFW, boolean isInvisible) {
		super();
		this.user = user;
		this.goods = goods;
		this.pingjia = pingjia;
		this.starSP = starSP;
		this.starWL = starWL;
		this.starFW = starFW;
		this.isInvisible = isInvisible;
		this.dateTimePJ = new Date();
	}
	public GoodsEntity getGoods() {
		return goods;
	}
	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public String getPingjia() {
		return pingjia;
	}
	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}
	public Date getDateTimeJY() {
		return dateTimeJY;
	}
	public void setDateTimeJY(Date dateTimeJY) {
		this.dateTimeJY = dateTimeJY;
	}
	public Date getDateTimePJ() {
		return dateTimePJ;
	}
	public void setDateTimePJ(Date dateTimePJ) {
		this.dateTimePJ = dateTimePJ;
	}
	public int getStarSP() {
		return starSP;
	}
	public void setStarSP(int starSP) {
		this.starSP = starSP;
	}
	public int getStarWL() {
		return starWL;
	}
	public void setStarWL(int starWL) {
		this.starWL = starWL;
	}
	public int getStarFW() {
		return starFW;
	}
	public void setStarFW(int starFW) {
		this.starFW = starFW;
	}
	public boolean isInvisible() {
		return isInvisible;
	}
	public void setInvisible(boolean isInvisible) {
		this.isInvisible = isInvisible;
	}
	public List<ImageEntity> getImageList() {
		return imageList;
	}
	public void setImageList(List<ImageEntity> imageList) {
		this.imageList = imageList;
	}


}
