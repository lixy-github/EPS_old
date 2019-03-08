package com.broadcontact.clothing.entity.supermarket;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.broadcontact.clothing.entity.ImageEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.entity.BaseEntity;
import com.broadcontact.common.hibernate.CommonDAO;

/**
 * 商品类
 */
@Entity
public class GoodsEntity extends BaseEntity{
	public final static int GOODS_DOWN = -1;		//下架
	public final static int GOODS_UP = 1;		//上架
	
	@ManyToOne
	private UserEntity user; // 发布人
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "goods")
	private List<ImageEntity> imageList = new ArrayList<ImageEntity>(); // 商品图片
	
	@OneToOne(cascade = CascadeType.REMOVE,mappedBy = "goods")
	private HTMLEntity html;			//商品详情
	
	private int goodsStatus;
	private String type; // 商品类别
	private String name; // 商品名
	private String guige; // 规格
	private String chengfen; // 成份
	private String chandi; // 产地
	private long kucun; // 库存
	private String danwei; // 单位
	private Double danjia; // 单价
	private String wuliuType; // 物流方式
	private String wuliuMoney; // 物流价格
	
	@Transient
	private boolean isShoucang;	//是否收藏

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public String getChengfen() {
		return chengfen;
	}

	public void setChengfen(String chengfen) {
		this.chengfen = chengfen;
	}

	public String getChandi() {
		return chandi;
	}

	public void setChandi(String chandi) {
		this.chandi = chandi;
	}

	public long getKucun() {
		return kucun;
	}

	public void setKucun(long kucun) {
		this.kucun = kucun;
	}

	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}

	public String getWuliuType() {
		return wuliuType;
	}

	public void setWuliuType(String wuliuType) {
		this.wuliuType = wuliuType;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public int getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(int goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public List<ImageEntity> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageEntity> imageList) {
		this.imageList = imageList;
	}

	public Double getDanjia() {
		return danjia;
	}

	public void setDanjia(Double danjia) {
		this.danjia = danjia;
	}
	@JSON(serialize=false)
	public HTMLEntity getHtml() {
		return html;
	}

	public void setHtml(HTMLEntity html) {
		this.html = html;
	}

	public String getWuliuMoney() {
		return wuliuMoney;
	}

	public void setWuliuMoney(String wuliuMoney) {
		this.wuliuMoney = wuliuMoney;
	}

	public boolean isShoucang() {
		return isShoucang;
	}

	public void setShoucang(boolean isShoucang) {
		this.isShoucang = isShoucang;
	}
	
	public static boolean updateKucun(GoodsEntity goods,long addNum,CommonDAO commonDAO) throws Exception{
		synchronized (goods.getId()) {
			long kucun = goods.getKucun();
			if(kucun + addNum < 0){
				return false;
			}else{
				goods.setKucun(kucun+addNum);  
			}
			commonDAO.save(goods);
		}
		return true;
	}
}
