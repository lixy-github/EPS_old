package com.broadcontact.clothing.action.supermarket;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.broadcontact.clothing.action.BaseAction;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.entity.supermarket.EvaluateEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsCollectionEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsEntity;
import com.broadcontact.common.hibernate.CommonDAO;
import com.broadcontact.common.hibernate.PaginationSupport;
import com.broadcontact.common.tools.TextUtil;

@Controller
@Scope("prototype")
public class SupermarketAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GoodsEntity goods;
	private String random;
	private String name;
	private String type;
	private Double price1;
	private Double price2;
	private int type1;
	@Resource
	private CommonDAO commonDAO;

	public String goodsDetail() throws Exception{
		goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", getRandom()).iterator().next();
		DetachedCriteria d = DetachedCriteria.forClass(EvaluateEntity.class);
		d.add(Restrictions.eq("goods", goods));
		d.addOrder(Order.desc("dateTimePJ"));
		keyword = "&random="+random;
		pageSupport = commonDAO.query(d, start, pageSize, keyword,"pingjia");
		return SUCCESS;
	}
	public String listGoods() throws Exception{
		DetachedCriteria d = DetachedCriteria.forClass(GoodsEntity.class);		
		d.add(Restrictions.eq("goodsStatus",GoodsEntity.GOODS_UP));
		d = filterGoods(d);
		pageSupport = commonDAO.query(d, start, 36, keyword);
		UserEntity user = getCurrUser();
		if(user!=null){
			List<BigInteger> list = commonDAO.selectSql("select goods_id from GoodsCollectionEntity where user_id="+user.getId());
			List<GoodsEntity> goodsList = pageSupport.getItems();
			for(GoodsEntity goods : goodsList){
				for(BigInteger goodId : list){
					if(goods.getId() == goodId.longValue()){
						goods.setShoucang(true);
					}
				}
			}		
		}
		return SUCCESS;
	}
	private DetachedCriteria filterGoods(DetachedCriteria d){
//		d.add(Restrictions.or(Restrictions.ne("status",-1L), Restrictions.isNull("status")));
		if(!TextUtil.isEmpty(name)){
			d.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(!TextUtil.isEmpty(type)){
			d.add(Restrictions.eq("type", type));
		}
		if(price1 != null){
			d.add(Restrictions.ge("danjia", price1));
		}
		if(price2 != null){
			d.add(Restrictions.le("danjia", price2));
		}
		return d;
	}
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	public GoodsEntity getGoods() {
		return goods;
	}
	public void setGoods(GoodsEntity goods) {
		this.goods = goods;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPrice1() {
		return price1;
	}
	public void setPrice1(Double price1) {
		this.price1 = price1;
	}
	public Double getPrice2() {
		return price2;
	}
	public void setPrice2(Double price2) {
		this.price2 = price2;
	}
	public int getType1() {
		return type1;
	}
	public void setType1(int type1) {
		this.type1 = type1;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
}
