package com.broadcontact.clothing.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadcontact.clothing.entity.DemandEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.hibernate.CommonDAO;
import com.broadcontact.common.hibernate.PageBean;

@Component()
@Scope("prototype")
public class DemandEntityAction extends BaseAction{

	@Resource
	private CommonDAO commonDAO;
	private Map<String, Object> map;
	private DemandEntity demandEntity;
	private UserEntity userEntity;
	
	//分页 初始化时因为不传也不报错
	private int currentPage = 1;
	private int pageSize = 3;
	PageBean pageBean;
	
	//供应链中两个检索
	private String demandType;//需求类别
	private String keyword;//关键字
	
	private String linkFlag;
	
	public String listDemandNeedHandle() throws Exception {
		linkFlag = "listDemandNeedHandle";
		getDemand();
		return SUCCESS;
	}
	
	public String releaseDemand() throws Exception {
		
		userEntity = (UserEntity) commonDAO.selectById(UserEntity.class, getCurrUser().getId());
		boolean flag = checkUserAuthority(getCurrUser().getId(), commonDAO);
		if(!flag){
			map = getJsonResult();
			return ERROR;
		}
		
		if(demandEntity!=null && demandEntity.getRandom()!=null){
			//正常情况下 是一定执行正确的
			try {
				demandEntity = (DemandEntity) commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom()).iterator().next();
			} catch (Exception e) {
				msg = "系统错误！可联系管理员";
				map = getJsonResult();
				return ERROR;
			}
		}else{
			demandEntity = new DemandEntity();//防止前台为null报错
		}
		
		return SUCCESS;
	}
	
	public String listAllDemand() throws Exception {
		String hql = "FROM DemandEntity d";
		List<Object> parameters = new ArrayList<Object>();
		if(demandType!=null && !"".equals(demandType)){
			if(hql.indexOf("where")!=-1){
				hql += " and d.demandType= ?";
			}else{
				hql += " where d.demandType= ?";
			}
			parameters.add(demandType);
		}
		
		if(keyword!=null && !"".equals(keyword)){
			if(hql.indexOf("where")!=-1){
				hql += " and d.ggyq like ?";
			}else{
				hql += " where d.ggyq like ? ";
			}
			parameters.add('%' + keyword + '%');
		}
		hql += " ORDER BY id DESC";
		pageBean = commonDAO.getPageBean(currentPage, pageSize, hql, parameters);
		
		getRequest().setAttribute("isChain", "yes");
		return SUCCESS;
	}
		
	public String listDemandForUser() throws Exception {
		
		getDemand();
		
		return SUCCESS;
	}

	private void getDemand() {
		String hql = "FROM DemandEntity d WHERE d.userEntity = ?";
		if("listDemandNeedHandle".equals(linkFlag)){
			hql += " and d.status = 1";
		}
		hql += "ORDER BY id DESC";
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(getCurrUser());
		pageBean = commonDAO.getPageBean(currentPage, pageSize, hql, parameters);
	}
	
	public String saveDemand() throws Exception {
		
		Long userId = (Long) getSession().getAttribute("_USERID_");
		boolean flag = super.checkUserAuthority(userId,commonDAO);
		if(!flag){
			map = getJsonResult();
			return ERROR;
		}
		try {
			if(demandEntity.getRandom()!=null && !"".equals(demandEntity.getRandom())){
				DemandEntity updateEntity = (DemandEntity) commonDAO.findByField(DemandEntity.class, "random", demandEntity.getRandom()).iterator().next();
				updateEntity.setDemandType(demandEntity.getDemandType());
				if(!"工序加工".equals(demandEntity.getDemandType())){
					updateEntity.setDemandTypeChildren(null);
				}else{
					updateEntity.setDemandTypeChildren(demandEntity.getDemandTypeChildren());
				}
				updateEntity.setGgyq(demandEntity.getGgyq());
				updateEntity.setSlyq(demandEntity.getSlyq());
				updateEntity.setSjyq(demandEntity.getSjyq());
				updateEntity.setMoneyStart(demandEntity.getMoneyStart());
				updateEntity.setMoneyEnd(demandEntity.getMoneyEnd());
				updateEntity.setMemo(demandEntity.getMemo());
				
				updateEntity.setTime(new Date());
				commonDAO.update(updateEntity);
				msg = "修改成功";
				
			}else{
				if(!"工序加工".equals(demandEntity.getDemandType())){
					demandEntity.setDemandTypeChildren(null);
				}
				
				UserEntity currUser = getCurrUser();
				demandEntity.setUserEntity(currUser);
				
				demandEntity.setRandom(createRandom());
				demandEntity.setTime(new Date());
				
				commonDAO.save(demandEntity);
				msg = "保存成功";
			}
			
		} catch (Exception e) {
			msg = "保存失败";
			retVal = -1;
		}

		map = getJsonResult();
		
		return SUCCESS;
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

	public DemandEntity getDemandEntity() {
		return demandEntity;
	}

	public void setDemandEntity(DemandEntity demandEntity) {
		this.demandEntity = demandEntity;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLinkFlag() {
		return linkFlag;
	}

	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
}
