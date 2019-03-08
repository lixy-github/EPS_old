package com.broadcontact.clothing.action.supermarket;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.broadcontact.clothing.action.BaseAction;
import com.broadcontact.clothing.entity.AddressEntity;
import com.broadcontact.clothing.entity.ImageEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.entity.supermarket.EvaluateEntity;
import com.broadcontact.clothing.entity.supermarket.EvaluateSallerEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsCartEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsCollectionEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsOrderEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsOrderItemEntity;
import com.broadcontact.clothing.entity.supermarket.HTMLEntity;
import com.broadcontact.common.hibernate.CommonDAO;
import com.broadcontact.common.tools.TextUtil;
import com.broadcontact.common.tools.Tools;

@Controller
@Scope("prototype")
public class GoodsAction extends BaseAction {	
	private GoodsEntity goods;
	private UserEntity user;
	private AddressEntity address;
	private GoodsOrderEntity order;
	private Map map = new HashMap();
	private Map map1 = new HashMap();
	private String random;
	@Resource
	private CommonDAO commonDAO;
	private File file;
	private String fileContentType;
	private int status;
	private String statusStr;
	private List<GoodsEntity> goodsList = new ArrayList<GoodsEntity>();
	private String content;
	private int num;
	private long id;
	private boolean isCheck;
	private double totalMoney;
	private int totalNum;
	private String reason;
	private String expressNumber;
	private String t;
	private int starNum;
	
	public String toUserEvaluate() throws Exception{
		order = (GoodsOrderEntity) commonDAO.findByField(GoodsOrderEntity.class, "random", random).iterator().next();
		return SUCCESS;
	}
	
	public String toSellerEvaluate() throws Exception{
		order = (GoodsOrderEntity) commonDAO.findByField(GoodsOrderEntity.class, "random", random).iterator().next();
		return SUCCESS;
	}
	
	public String saveSellerEvaluate() throws Exception{
		GoodsOrderEntity order = (GoodsOrderEntity) commonDAO.selectById(GoodsOrderEntity.class, id);
		order.setSellerEvaluateOver(true);
		if(TextUtil.isEmpty(reason)){
			reason = "这个用户很懒，什么也没留下";
		}
		EvaluateSallerEntity evaluate = new EvaluateSallerEntity(order,starNum,reason);
		commonDAO.update(order);
		commonDAO.save(evaluate);
		return SUCCESS;
	}
	
	public String saveUserEvaluate() throws Exception{
		System.out.println(t);
		UserEntity user = getCurrUser();
		JSONArray jsonArray = JSONArray.fromObject(t);
		GoodsOrderEntity order = (GoodsOrderEntity) commonDAO.selectById(GoodsOrderEntity.class, id);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);		
			String goodsRandom = jsonObject.getString("goodsRandom");
			GoodsEntity goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", goodsRandom).iterator().next();
			int starSP = jsonObject.getInt("starSP");	
			int starFW = jsonObject.getInt("starFW");	
			int starWL = jsonObject.getInt("starWL");	
			String pingjia = jsonObject.getString("pingjia");	
			if(TextUtil.isEmpty(pingjia)){
				pingjia = "这个用户很懒，什么也没留下";
			}
			boolean isInvisible = jsonObject.getBoolean("isInvisible");
			EvaluateEntity evaluate = new EvaluateEntity(user, goods, pingjia, starSP, starWL, starFW, isInvisible);
			evaluate.setDateTimeJY(order.getCreateTime());
			commonDAO.save(evaluate);
			JSONArray pathArr = jsonObject.getJSONArray("pathArr");
			for(int k=0;k<pathArr.size();k++){
				String path = pathArr.getString(k);
				ImageEntity image = new ImageEntity();
				image.setPath(path);
				image.setUserEvaluate(evaluate);
				commonDAO.save(image);
			}
			order.setUserEvaluateOver(true);
		}
		return SUCCESS;
	}	
	
	public String listSalesOrder() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria d = DetachedCriteria.forClass(GoodsOrderEntity.class);
		d.add(Restrictions.eq("seller", user));
		d.addOrder(Order.desc("createTime"));
		d.add(Restrictions.isEmpty("children"));
		if(TextUtil.isEmpty(t)){
			d.add(Restrictions.eq("orderStatus", status));
		}
		if(status == GoodsOrderEntity.ORDERSTATUS_YWC){
			d.add(Restrictions.eq("isSellerEvaluateOver", false));
		}
		pageSupport = commonDAO.query(d, start, pageSize, null);
		return SUCCESS;
	}
	
	public String getOrderDetail() throws Exception{
		order = (GoodsOrderEntity) commonDAO.findByField(GoodsOrderEntity.class, "random", getRandom()).iterator().next();
		return SUCCESS;
	}
	
	public String updateOrderStatus() throws Exception{
		GoodsOrderEntity order = (GoodsOrderEntity) commonDAO.findByField(GoodsOrderEntity.class, "random", getRandom()).iterator().next();
		if(status == GoodsOrderEntity.ORDERSTATUS_YQX){		//取消订单
			order.setOrderStatus(status);
			order.setCancelReason(reason);
			for(GoodsOrderItemEntity item:order.getItems()){
				GoodsEntity goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", item.getGoodsRandom()).iterator().next();
				GoodsEntity.updateKucun(goods, item.getPiece(), commonDAO);
			}
			commonDAO.update(order);
		}
		if(status == GoodsOrderEntity.ORDERSTATUS_YZF){	 //支付
			order.setOrderStatus(status);
			//do something
			commonDAO.update(order);
		}
		if(status == GoodsOrderEntity.ORDERSTATUS_YFH){	 //发货
			order.setOrderStatus(status);
			order.setExpressNumber(expressNumber);
			commonDAO.update(order);
		}
		if(status == GoodsOrderEntity.ORDERSTATUS_YWC){	 //签收
			order.setOrderStatus(status);
			commonDAO.update(order);
		}
		
		map.put("status", 0);
		map.put("msg", "更新成功");
		return SUCCESS;
	}
	
	public String removeOrder() throws Exception{
		GoodsOrderEntity order = (GoodsOrderEntity) commonDAO.findByField(GoodsOrderEntity.class, "random", getRandom()).iterator().next();
		commonDAO.delete(order);
		map.put("status", 0);
		map.put("msg", "删除成功");
		return SUCCESS;
	}
	
	public String listPurchaseOrder() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria d = DetachedCriteria.forClass(GoodsOrderEntity.class);
		d.add(Restrictions.eq("user", user));
		d.addOrder(Order.desc("createTime"));
		d.add(Restrictions.isEmpty("children"));
		pageSupport = commonDAO.query(d, start, pageSize, null);
		return SUCCESS;
	}
	
	public String removeAddress(){
		try {
			AddressEntity address = (AddressEntity) commonDAO.selectById(AddressEntity.class, id);
			commonDAO.delete(address);
		} catch (Exception e) {}
		return SUCCESS;
	}
	
	public String addAddress() throws Exception{
		if(address.getId() == null){
			address.setCreateTime(new Date());
			address.setUser(getCurrUser());
			address.setRandom(createRandom());
		}
		commonDAO.saveOrUpdate(address);
		map.put("address", address);
		return SUCCESS;
	}
	
	public String updateAddressDefault() throws Exception{
		AddressEntity address = (AddressEntity) commonDAO.selectById(AddressEntity.class, id);
		address.setCreateTime(new Date());
//		commonDAO.update(address);
		return SUCCESS;
	}
	
	public String listCart() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsCartEntity.class);
		dc.add(Restrictions.eq("user", user));
		List<GoodsCartEntity> carts = commonDAO.query(dc);
		map = new LinkedHashMap<>();
		for(GoodsCartEntity cart : carts){
			UserEntity userKey = cart.getGoods().getUser();
			List l = (List) map.get(userKey);
			if(l == null){
				map.put(userKey, new ArrayList(Arrays.asList(cart)));
			}else{
				l.add(cart);
			}
		}
		setCheckedMap();	//是否   店铺选中 全部选中
		return SUCCESS;
	}
	public String updateCartNumSession() throws Exception{
		Iterator<GoodsCartEntity> it = commonDAO.findByField(GoodsCartEntity.class, "user",getCurrUser()).iterator();
		int num = 0;
		while(it.hasNext()){
			GoodsCartEntity cart = it.next();
			num += cart.getGoodsNum();
		}
		getSession().setAttribute("_CART_GOODS_NUM_", num);
		map.put("num", num);
		return SUCCESS;
	}
	
	public String updateCartNum1() throws Exception{
		try {
			GoodsCartEntity cart = (GoodsCartEntity) commonDAO.selectById(GoodsCartEntity.class, id);
			int goodsNum = cart.getGoodsNum();
			cart.setGoodsNum(num);
			commonDAO.update(cart);
			updateCartNumSession();
		} catch (Exception e) {}
		return SUCCESS;
	}
	
	public String removeCart() throws Exception{
		try {
			GoodsCartEntity cart = (GoodsCartEntity) commonDAO.selectById(GoodsCartEntity.class, id);
			commonDAO.delete(cart);
		} catch (Exception e) {}
		return SUCCESS;
	}
	
	private void setCheckedMap() {
		List<List<GoodsCartEntity>> vals = new ArrayList<>(map.values());
		boolean isCheckAll = true;
		for(List<GoodsCartEntity> l:vals){
			boolean isCheckSome = true;
			for(GoodsCartEntity cart : l){
				if(!cart.isCheck()){
					isCheckSome = false;
					break;
				}
			}
			map1.put(l.get(0).getGoods().getUser(), isCheckSome);
			if(!isCheckSome){
				isCheckAll = false;
			}
		}
		map1.put("all", isCheckAll);
	}
	
	public String updateCartChecked() throws Exception{
		GoodsCartEntity cart = (GoodsCartEntity) commonDAO.selectById(GoodsCartEntity.class, id);
		cart.setCheck(!cart.isCheck());
		commonDAO.update(cart);
		map.put("isCheck", cart.isCheck());
		return SUCCESS;
	}
	/*
	 * user购买者
	 * id 商品id
	 * num 商品添加数量
	 */
	public String updateCart() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsCartEntity.class);
		dc.add(Restrictions.eq("user", user));
		GoodsEntity g = new GoodsEntity();
		g.setId(id);
		dc.add(Restrictions.eq("goods", g));
		GoodsCartEntity cart = null;
		Iterator<GoodsCartEntity> it = commonDAO.query(dc).iterator();
		if(it.hasNext()){
			cart = it.next();
			num = cart.getGoodsNum() + num;
			if(num <= 0){
				commonDAO.delete(cart);
				return SUCCESS;
			}else{
				cart.setGoodsNum(num);
			}
		}else{
			GoodsEntity goods = new GoodsEntity();
			goods.setId(id);
			cart = new GoodsCartEntity(user, goods, num);
		}
		commonDAO.saveOrUpdate(cart);
		return SUCCESS;
	}

	public String myCollection() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsCollectionEntity.class);
		dc.add(Restrictions.eq("user", user));
		pageSupport = commonDAO.query(dc, start, 15, "");
		return SUCCESS;
	}
	
	public String addOrder() throws Exception{
		getOrderInfo();
		UserEntity user = getCurrUser();
		AddressEntity receiveAddress = (AddressEntity) commonDAO.selectById(AddressEntity.class, id);
		GoodsOrderEntity parentOrder =  (GoodsOrderEntity) map.get("parentOrder");
		if(parentOrder != null){
			if(parentOrder.getTotalNum() == 0){
				return ERROR;
			}
			parentOrder.setReceiveAddress(receiveAddress);
			commonDAO.save(parentOrder);
		}
		List<GoodsOrderEntity> orderList = (List<GoodsOrderEntity>) map.get("orderList");
		for(GoodsOrderEntity order : orderList){
			if(order.getTotalNum() == 0){
				continue;  //缺货 没意义的订单
			}
			order.setReceiveAddress(receiveAddress);
			commonDAO.save(order);
			for(GoodsOrderItemEntity item : order.getItems()){
				GoodsEntity goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", item.getGoodsRandom()).iterator().next();
				if(!GoodsEntity.updateKucun(goods, -item.getPiece(), commonDAO)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return ERROR;
				}
				deleteCartByRandom(user, item.getGoodsRandom());
			}
		}
		updateCartNumSession();
		return SUCCESS;
	}
	private void deleteCartByRandom(UserEntity user, String random)
			throws Exception {
		DetachedCriteria d = DetachedCriteria.forClass(GoodsCartEntity.class);
		d.add(Restrictions.eq("user", user));
		d.createAlias("goods", "goods");
		d.add(Restrictions.eq("goods.random", random));
		Iterator<GoodsCartEntity> it = commonDAO.query(d).iterator();
		if(it.hasNext()){
			GoodsCartEntity cart = it.next();
			commonDAO.delete(cart);
		}
	}
	
	public String getOrderInfo() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria d = DetachedCriteria.forClass(GoodsCartEntity.class);
		d.add(Restrictions.eq("user", user));
		d.add(Restrictions.eq("isCheck", true));
		Iterator<GoodsCartEntity> it = commonDAO.query(d).iterator();
		Map<UserEntity,List<GoodsCartEntity>> addr_list_map = new HashMap<>();
		while(it.hasNext()){
			GoodsCartEntity cart = it.next();
			UserEntity userKey = cart.getGoods().getUser();
			List<GoodsCartEntity> cartList = addr_list_map.get(userKey);
			if(cartList == null){
				addr_list_map.put(userKey, new ArrayList(Arrays.asList(cart)));
			}else{
				cartList.add(cart);
			}
		}
		
		List<GoodsOrderEntity> list = new ArrayList<>();
		Iterator<Entry<UserEntity, List<GoodsCartEntity>>> iterator = addr_list_map.entrySet().iterator();
		while(iterator.hasNext()){								//不同店家 创建对应的订单
			Entry<UserEntity, List<GoodsCartEntity>> entry = iterator.next();
			GoodsOrderEntity order = new GoodsOrderEntity(user, entry.getKey());
			List<GoodsCartEntity> vals = entry.getValue();
			for(GoodsCartEntity cart : vals){
				boolean isEnough = cart.getGoods().getKucun() > cart.getGoodsNum();	
				GoodsOrderItemEntity item = new GoodsOrderItemEntity(cart);
				if(isEnough){		//库存充足
					item.setEnough(isEnough);
					order.setTotalMoney(order.getTotalMoney() + item.getDanjia() * item.getPiece());
					order.setTotalNum(order.getTotalNum()+item.getPiece());
				}
				item.setOrder(order);
				order.getItems().add(item);
			}
			list.add(order);
		}
		if(list.size() > 1){		//多个订单 创建父订单
			GoodsOrderEntity parent = new GoodsOrderEntity(user);
			for(GoodsOrderEntity child : list){
				child.setParent(parent);
				parent.setTotalMoney(parent.getTotalMoney() + child.getTotalMoney());
				parent.setTotalNum(parent.getTotalNum()+child.getTotalNum());
				parent.getChildren().add(child);
			}
			map.put("parentOrder", parent);
			totalMoney = parent.getTotalMoney();
			totalNum = parent.getTotalNum();
		}else{
			if(list.size() == 0)
				return "listCart";
			GoodsOrderEntity order = list.get(0);
			totalMoney = order.getTotalMoney();
			totalNum = order.getTotalNum();
		}
		map.put("orderList", list);
		map.put("addressList", listUserAddress(user));  //收货地址
		return SUCCESS;
	}
	
	private List<AddressEntity> listUserAddress(UserEntity user) throws Exception{
		DetachedCriteria d = DetachedCriteria.forClass(AddressEntity.class);
		d.add(Restrictions.eq("user", user));
		d.addOrder(Order.desc("createTime"));
		return commonDAO.query(d);
	}
	
	public String myOrder() throws Exception{
		UserEntity user = getCurrUser();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsOrderEntity.class);
		dc.add(Restrictions.eq("user", user));
		dc.addOrder(Order.desc("createTime"));
		list = commonDAO.query(dc);
		return SUCCESS;
	}
	
	public String addCollection() throws Exception{
		UserEntity user = getCurrUser();
		GoodsEntity goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", getRandom()).iterator().next();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsCollectionEntity.class);
		dc.add(Restrictions.eq("user", user));
		dc.add(Restrictions.eq("goods", goods));
		list = commonDAO.query(dc);
		if(list.size()>0){
			map.put("msg", "收藏成功");
			map.put("status", 1);
			return SUCCESS;
		}
		GoodsCollectionEntity collection = new GoodsCollectionEntity();
		collection.setCreateTime(new Date());
		collection.setGoods(goods);
		collection.setUser(user);
		collection.setRandom(createRandom());
		commonDAO.save(collection);
		map.put("msg", "收藏成功");
		map.put("status", 0);
		return SUCCESS;
	}
	public String removeCollection() throws Exception{
		GoodsCollectionEntity collection = (GoodsCollectionEntity) commonDAO.findByField(GoodsCollectionEntity.class, "random", getRandom()).iterator().next();
		commonDAO.delete(collection);
		map.put("msg", "移除成功");
		map.put("status", 0);
		return SUCCESS;
	}
	public String goUpdateGoods() throws Exception{
		goods = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", getRandom()).iterator().next();
		return SUCCESS;
	}
	public String listMyGoods() throws Exception{
		switch (status) {
		case 0:
			statusStr = "未处理";
			break;
		case 1:
			statusStr = "已上架";
			break;
		case -1:
			statusStr = "已下架";
			break;
		case 100:
			statusStr = "全部";
			break;
		}
		DetachedCriteria d = DetachedCriteria.forClass(GoodsEntity.class);
		d.add(Restrictions.or(Restrictions.ne("status",-1L), Restrictions.isNull("status")));
		if(status != 100){
			d.add(Restrictions.eq("goodsStatus",status));
		}
		d.addOrder(Order.desc("id"));
		keyword = "status="+status;
		pageSupport = commonDAO.query(d, start, 12, keyword);
		return SUCCESS;
	}
	public String saveGoodsImage() throws Exception{
		Long id = goods.getId();
		if(id == null){	
			map.put("status", -1);
			map.put("msg", "请先填写基本信息");
			return SUCCESS;
		}
		String dateStr = Tools.processDate(new Date());
		String name = String.valueOf(createRandom()+"."+fileContentType.split("/")[1]);
		if(getFile()!=null){
			String filePath = ServletActionContext.getServletContext().getRealPath("/upload/goods/"+dateStr);
			File dirFile = new File(filePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			} 
			String smallPath = ServletActionContext.getServletContext().getRealPath("/upload/goods_200*200/"+dateStr);
			File dirFile1 = new File(smallPath);
			if (!dirFile1.exists()) {
				dirFile1.mkdirs();
			}
            FileUtils.copyFile(getFile(), new File(filePath,name));				//原图
            Thumbnails.of(getFile()).size(400, 400).toFile(new File(smallPath,name));	//缩略图
		}	
		GoodsEntity g = (GoodsEntity) commonDAO.selectById(GoodsEntity.class, id);	
		ImageEntity image = new ImageEntity();
		image.setGoods(g);
		image.setPath("/upload/goods/"+dateStr+"/"+name);
		image.setSmallPath("/upload/goods_200*200/"+dateStr+"/"+name);
		image.setRandom(createRandom());
		commonDAO.save(image);
		map.put("imageId", image.getRandom());
		map.put("status", 0);
		map.put("msg", "上传成功");
		return SUCCESS;
	}
	public String removeGoodsImage() throws Exception{
		try{
			ImageEntity image = (ImageEntity) commonDAO.findByField(ImageEntity.class, "random", getRandom()).iterator().next();
			commonDAO.delete(image);
			map.put("status", 0);
			map.put("msg", "删除成功");
		}catch(Exception e){
			map.put("status", -1);
			map.put("msg", "删除失败");
		}
		return SUCCESS;
	}
	public String saveGoods() throws Exception{
		UserEntity user = getCurrUser();
		goods.setUser(user);
		if(TextUtil.isEmpty(goods.getRandom())){
			goods.setRandom(createRandom());
			commonDAO.save(goods);
		}else{
			commonDAO.update(goods);
		}
		map.put("id", goods.getId());
		map.put("random", goods.getRandom());
		return SUCCESS;
	}
	public String updateGoodsStatus() throws Exception{
		GoodsEntity g = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", goods.getRandom()).iterator().next();
		g.setGoodsStatus(status);
		commonDAO.update(g);
		return SUCCESS;
	}
	
	public String saveGoodsHtml() throws Exception{
		if(TextUtil.isEmpty(goods.getRandom())){
			map.put("status", "-1");
			map.put("msg", "请先填写基本信息");
		}else{
			GoodsEntity g = (GoodsEntity) commonDAO.findByField(GoodsEntity.class, "random", goods.getRandom()).iterator().next();
			HTMLEntity html = g.getHtml();
			if(html == null){
				html = new HTMLEntity();
			}
			html.setContent(content);
			html.setGoods(g);	
			commonDAO.saveOrUpdate(html);
			map.put("status", "0");
			map.put("msg", "成功");
		}
		return SUCCESS;
	}
	public String findGoods(){
		
		return SUCCESS;
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
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public List<GoodsEntity> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsEntity> goodsList) {
		this.goodsList = goodsList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public Map getMap1() {
		return map1;
	}
	public void setMap1(Map map1) {
		this.map1 = map1;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public GoodsOrderEntity getOrder() {
		return order;
	}

	public void setOrder(GoodsOrderEntity order) {
		this.order = order;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public int getStarNum() {
		return starNum;
	}

	public void setStarNum(int starNum) {
		this.starNum = starNum;
	}
	
}
