package com.broadcontact.clothing.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadcontact.clothing.entity.AdminEntity;
import com.broadcontact.clothing.entity.DemandEntity;
import com.broadcontact.clothing.entity.ManufacturerEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.entity.UserNewsEntity;
import com.broadcontact.common.hibernate.CommonDAO;

@Component()
@Scope("prototype")
public class AdminEntityAction extends BaseAction{

	@Resource
	private CommonDAO commonDAO;
	private Map<String, Object> map = new HashMap<String, Object>();
	private AdminEntity adminEntity;
	private UserEntity userEntity;
	private DemandEntity demandEntity;
	private ManufacturerEntity manufacturerEntity;
	private String selName;//待审核or已审核or审核不通过
	private String paramOp;//审核：通过 或者 不通过
	private String remarks;//不通过填写的信息
	private String advantages;
	private String keyword;
	private String selectCnHid;//需求匹配公司的时候 公司的random字符串 xxx,xxx,xxx
	
	public String saveDemandPiPeiCompany() throws Exception{
		
		Set<UserEntity> allCompany = new HashSet<UserEntity>();
		String selectCnHidArr[] = selectCnHid.split(",");
		for (int i = 0; i < selectCnHidArr.length; i++) {
			try {
				UserEntity company = (UserEntity) commonDAO.findByField(UserEntity.class,"random",selectCnHidArr[i]).iterator().next();
				allCompany.add(company);
			} catch (Exception e) {
				msg = "random无效";
				return ERROR;
			}
		}
		
		try {
			demandEntity = (DemandEntity) commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom()).iterator().next();
			demandEntity.setCompanySet(allCompany);
			demandEntity.setStatus(DemandEntity.STATUS_HANDLE);
			try {
				commonDAO.save(demandEntity);
				msg = "操作成功";
			} catch (Exception e) {
				msg = "操作失败";
				retVal = -1;
			}
		
		} catch (Exception e) {
			msg = "random无效";
			return ERROR;
		}
		
		map = getJsonResult();
		
		return SUCCESS;
	}
	
	public String getUserEntityByKeyword() throws Exception{
		
		try {
			demandEntity = (DemandEntity) commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom()).iterator().next();
		} catch (Exception e) {
			msg = "random无效";
			return ERROR;
		}
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(UserEntity.class);
		dc.add(Expression.eq("userType", "1"));//已审核
		dc.add(Expression.ne("id", demandEntity.getUserEntity().getId()));//过滤掉自己
		if(!"".equals(keyword)){
			dc.add(Expression.like("companyName", "%"+keyword+"%"));
		}
		
		try {
			List<UserEntity> users = commonDAO.query(dc);
			jsonUser(users);
			msg = "获取成功";
			
		} catch (Exception e) {
			msg = "获取失败";
			retVal = -1;
		}
		map = getJsonResult();
		return SUCCESS;
	}
	
	public String getUserEntityByAdvantage() throws Exception{
		
		try {
			demandEntity = (DemandEntity) commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom()).iterator().next();
		} catch (Exception e) {
			msg = "random无效";
			return ERROR;
		}
		
		String hql="FROM UserEntity WHERE userType='1' and id <>"+demandEntity.getUserEntity().getId(); //过滤掉自己
		
		if(!"".equals(advantages)){
			String[] advantagesArr = advantages.split(",");
			
			
			hql += " AND";
			if(advantagesArr.length==1){
				hql += " advantage LIKE '%"+advantagesArr[0]+"%'";
			}else{
				hql += " (";
				for(int i=0;i<advantagesArr.length;i++){
					if(i==0){
						hql += "advantage LIKE '%"+advantagesArr[i]+"%'";
					}else{
						hql += " OR advantage LIKE '%"+advantagesArr[i]+"%'";
					}
				}
				hql += ")";
			}
		}
		
		try {
			List<UserEntity> users = commonDAO.selectHql(hql);
			jsonUser(users);
			msg = "获取成功";
		} catch (Exception e) {
			msg = "获取失败";
			retVal = -1;
		}
		
		map = getJsonResult();
		
		return SUCCESS;
	}

	private void jsonUser(List<UserEntity> users) {
		list = new ArrayList<UserEntity>();
		for (UserEntity userEntity : users) {
			UserEntity newUser = new UserEntity();
			newUser.setId(userEntity.getId());
			newUser.setCompanyName(userEntity.getCompanyName());
			newUser.setRandom(userEntity.getRandom());
			list.add(newUser);
		}
	}
	
	public String getDemandInfo() throws Exception{
		
		List<DemandEntity> demands = commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom());
		if(demands.size()!=1){
			msg = "random无效";
			retVal = -1;
			map = getJsonResult();
			return ERROR;
		}else{
			demandEntity = demands.get(0);
			
			if(new Long(1)==demandEntity.getStatus()){
				Set<UserEntity> set = demandEntity.getCompanySet();
				if(set.size()==0){//不可能出现状态已经匹配 但又没找到匹配公司的情况
					msg = "系统错误";
					retVal = -1;
					map = getJsonResult();
					return ERROR;
				}else{
					Iterator<UserEntity> it = set.iterator();
					selectCnHid = "";
					while(it.hasNext()){
						UserEntity company = it.next();
						selectCnHid += company.getCompanyName()+",";
					}
				}
			}
			msg = "成功";
		}
		
		getRequest().setAttribute("isAdminPage", "yes");
		
		return SUCCESS;
	}
	
	public String getDemand() throws Exception{
		
		DetachedCriteria dc = DetachedCriteria.forClass(DemandEntity.class);
		
		if(selName==null || "dcl".equals(selName)){
			dc.add(Expression.isNull("status"));
		}else if("ycl".equals(selName)){
			dc.add(Expression.eq("status", 1L));
		}
		
		tableSearchAndOrder(dc);
		
		pageSupport = commonDAO.query(dc, null, rows * (page - 1), rows);
		
		list = pageSupport.getItems();
		
		map.put("rows", list);
		map.put("page", page);
		map.put("total", pageSupport.getTotalPage());
		map.put("records", list.size());
		
		return SUCCESS;
	}
	
	public String updateManufacturerStatus() throws Exception{
		
		List<ManufacturerEntity> manufacturers = commonDAO.findByField(ManufacturerEntity.class, "random", manufacturerEntity.getRandom());
		if(manufacturers.size()!=1){
			msg = "random无效";
			retVal = -1;
			map = getJsonResult();
			return ERROR;
		}else{
			try {
				ManufacturerEntity updateEntity = manufacturers.get(0);
				
				if("pass".equals(paramOp)){
					updateEntity.setStatus(ManufacturerEntity.STATUS_PASS);
					
					updateEntity.setJqsbsl(manufacturerEntity.getJqsbsl());
					updateEntity.setGrsl(manufacturerEntity.getGrsl());
					updateEntity.setPkgldj(manufacturerEntity.getPkgldj());
					updateEntity.setZdrcl(manufacturerEntity.getZdrcl());
					
				}else if("noPass".equals(paramOp)){
					updateEntity.setStatus(ManufacturerEntity.STATUS_FAILED);
					
					userEntity = (UserEntity) commonDAO.selectById(UserEntity.class, updateEntity.getUserId());
					Set<UserEntity> set = new HashSet<UserEntity>();
					set.add(userEntity);
					UserNewsEntity news = new UserNewsEntity();
					news.setContent(remarks);
					news.setRandom(createRandom());
					news.setTime(new Date());
					news.setType("2");
					news.setUserSet(set);
					commonDAO.save(news);
					
				}
				
				commonDAO.update(updateEntity);
				msg = "成功";
			} catch (Exception e) {
				msg = "失败";
				retVal = -1;
			}
		}
		map = getJsonResult();	
		return SUCCESS;
	}
	
	public String getManufacturerInfo() throws Exception{
		
		List<ManufacturerEntity> manufacturers = commonDAO.findByField(ManufacturerEntity.class, "random", manufacturerEntity.getRandom());
		if(manufacturers.size()!=1){
			msg = "random无效";
			retVal = -1;
			map = getJsonResult();
			return ERROR;
		}else{
			manufacturerEntity = manufacturers.get(0);
			
			userEntity = (UserEntity) commonDAO.selectById(UserEntity.class, manufacturerEntity.getUserId());
			getRequest().setAttribute("userinfo", userEntity.getCompanyName());
			
			if(3==manufacturerEntity.getStatus()){
				getRequest().setAttribute("remarks", super.getNewsStr(userEntity, "2"));
			}
		}
		
		getRequest().setAttribute("isAdminPage", "yes");
		
		return SUCCESS;
	}
	
	
	public String getManufacturerStatusEq123() throws Exception{
		DetachedCriteria dc = DetachedCriteria.forClass(ManufacturerEntity.class);
		
		if(selName==null || "dsh".equals(selName)){
			dc.add(Expression.eq("status", 2L));
		}else if("ysh".equals(selName)){
			dc.add(Expression.eq("status", 1L));
		}else if("shbtg".equals(selName)){
			dc.add(Expression.eq("status", 3L));
		}
		
		tableSearchAndOrder(dc);
		
		pageSupport = commonDAO.query(dc, null, rows * (page - 1), rows);
		
		list = pageSupport.getItems();
		
		map.put("rows", list);
		map.put("page", page);
		map.put("total", pageSupport.getTotalPage());
		map.put("records", list.size());
		
		return SUCCESS;
	}
	
	/**
	 * 审核用户信息 将userType字段null---> 1
	 * @return
	 * @throws Exception
	 */
	public String updateUserType() throws Exception{
		try {
			List<UserEntity> users = commonDAO.findByField(UserEntity.class, "random", userEntity.getRandom());
			if(users.size()!=1){
				msg = "系统错误";
				retVal = -1;
			}else{
				
				userEntity = users.get(0);
				
				if("pass".equals(paramOp)){
					userEntity.setUserType("1");
				}else if("noPass".equals(paramOp)){
					
					UserNewsEntity news = new UserNewsEntity();
					Set<UserEntity> set = new HashSet<>();
					set.add(userEntity);
					news.setContent(remarks);
					news.setTime(new Date());
					news.setRandom(super.createRandom());
					news.setType("1");
					news.setUserSet(set);
					commonDAO.save(news);
					
					userEntity.setUserType("3");
				}
				
				commonDAO.update(userEntity);
				msg = "更新成功";
			}
		} catch (Exception e) {
			retVal = -1;
			msg = "更新失败";
		}
		
		map = getJsonResult();
		return SUCCESS;
	}
	
	/**
	 * 根据用户Id 获取单个用户信息
	 * @return
	 * @throws Exception
	 */
	public String getUserInfoById() throws Exception{
		
		List<UserEntity> users = commonDAO.findByField(UserEntity.class, "random", userEntity.getRandom());
		if(users.size()!=1){
			msg = "random无效";
			retVal = -1;
			map = getJsonResult();
			return ERROR;
		}else{
			userEntity = users.get(0);
			
			if("3".equals(userEntity.getUserType())){
				getRequest().setAttribute("remarks",super.getNewsStr(userEntity, "1"));
			}
		}
		
		getRequest().setAttribute("isAdminPage", "yes");
		
		return SUCCESS;
	}
	
	/**
	 * 获取userType为1/2/3的list
	 * 默认取2 即用户提交待审核
	 * @return
	 */
	public String getAllUserTypeEq123() throws Exception{
		
		DetachedCriteria dc = DetachedCriteria.forClass(UserEntity.class);
		
		if(selName==null || "dsh".equals(selName)){
			dc.add(Expression.eq("userType", "2"));
		}else if("ysh".equals(selName)){
			dc.add(Expression.eq("userType", "1"));
		}else if("shbtg".equals(selName)){
			dc.add(Expression.eq("userType", "3"));
		}
		
		
		tableSearchAndOrder(dc);
		
		pageSupport = commonDAO.query(dc, null, rows * (page - 1), rows);
		
		List<UserEntity> tempList = pageSupport.getItems();
		
		list = new ArrayList<UserEntity>();
		//为了不让json数据过多 重新组装list
		for (UserEntity userEntity : tempList) {
			UserEntity user = new UserEntity();
			user.setId(userEntity.getId());
			user.setTel(userEntity.getTel());
			user.setEmail(userEntity.getEmail());
			user.setCompanyName(userEntity.getCompanyName());
			user.setAddress(userEntity.getAddress());
			user.setNsrsbh(userEntity.getNsrsbh());
			user.setLxPhone(userEntity.getLxPhone());
			user.setLxr(userEntity.getLxr());
			user.setRandom(userEntity.getRandom());
			
			list.add(user);
		}
		
		map.put("rows", list);
		map.put("page", page);
		map.put("total", pageSupport.getTotalPage());
		map.put("records", list.size());
		
		
		return SUCCESS;
	}
	
	/**
	 * 表格 查询 或者 排序
	 * @param dc
	 */
	private void tableSearchAndOrder(DetachedCriteria dc) {
		if("true".equals(_search)){ //搜索
			if("eq".equals(searchOper)){
				dc.add(Expression.eq(searchField, searchString));
			}else if("ne".equals(searchOper)){
				dc.add(Expression.ne(searchField, searchString));
			}else if("cn".equals(searchOper)){
				dc.add(Expression.like(searchField, "%"+searchString+"%"));
			}
		}
		
		if(sidx!=null && !"".equals(sidx)){
			if("asc".equals(sord)){
				dc.addOrder(Order.asc(sidx));
			}else if("desc".equals(sord)){
				dc.addOrder(Order.desc(sidx));
			}
		}
	}
	
	
	/**
	 * 管理员登录
	 * @return
	 * @throws Exception
	 */
	public String loginForAdmin() throws Exception{
		
		DetachedCriteria dc = DetachedCriteria.forClass(AdminEntity.class);
		dc.add(Expression.eq("tel", adminEntity.getTel()));
		List<AdminEntity> admins = commonDAO.query(dc);
		if(admins.size()<=0){
			msg = "该管理员账号不存在";
			retVal = -1;
		}else if(admins.size()>1){//该情况基本不出现
			msg = "多个账号，系统错误，联系管理员";
			retVal = -1;
		}else if(admins.size()==1){
			AdminEntity loginAdmin = admins.get(0);
			if(!loginAdmin.getPassword().equals(adminEntity.getPassword())){
				msg = "密码不正确";
				retVal = -1;
			}
			else{
				msg = "登录成功";
				setLoginAdminInfo(adminEntity);
			}
		}
		
		map = getJsonResult();
		return SUCCESS;
	}

	private void setLoginAdminInfo(AdminEntity adminEntity) {
		getSession().setAttribute("_ADMIN_", adminEntity);
		getSession().setAttribute("_ADMINID_", adminEntity.getId());
	}
	
	
	
	
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public AdminEntity getAdminEntity() {
		return adminEntity;
	}

	public void setAdminEntity(AdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getSelName() {
		return selName;
	}

	public void setSelName(String selName) {
		this.selName = selName;
	}


	public ManufacturerEntity getManufacturerEntity() {
		return manufacturerEntity;
	}


	public void setManufacturerEntity(ManufacturerEntity manufacturerEntity) {
		this.manufacturerEntity = manufacturerEntity;
	}

	public String getParamOp() {
		return paramOp;
	}

	public void setParamOp(String paramOp) {
		this.paramOp = paramOp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public DemandEntity getDemandEntity() {
		return demandEntity;
	}

	public void setDemandEntity(DemandEntity demandEntity) {
		this.demandEntity = demandEntity;
	}

	public String getAdvantages() {
		return advantages;
	}

	public void setAdvantages(String advantages) {
		this.advantages = advantages;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSelectCnHid() {
		return selectCnHid;
	}

	public void setSelectCnHid(String selectCnHid) {
		this.selectCnHid = selectCnHid;
	}
	
	
}
