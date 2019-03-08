package com.broadcontact.clothing.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadcontact.clothing.entity.ManufacturerEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.common.hibernate.CommonDAO;

@Component()
@Scope("prototype")
public class ManufacturerEntityAction extends BaseAction {
	
	@Resource
	private CommonDAO commonDAO;
	private Map<String, Object> map;
	private ManufacturerEntity manufacturerEntity;
	
	public String manufacturer() throws Exception {
		
		Long currId = (Long) getSession().getAttribute("_USERID_");
		
		List<ManufacturerEntity> fs = commonDAO.findByField(ManufacturerEntity.class, "userId", currId);
		if(fs.size()>1){
			msg = "错误:工厂认证信息多个";
			map = getJsonResult();
			return ERROR;
		}
		if(fs.size()==1){
			manufacturerEntity = fs.get(0);
			
			if(3==manufacturerEntity.getStatus()){
				UserEntity userEntity = (UserEntity) commonDAO.selectById(UserEntity.class, manufacturerEntity.getUserId());
				String result = getNewsStr(userEntity,"2");
				getRequest().setAttribute("remarks", result);
			}
		}else{
			manufacturerEntity = new ManufacturerEntity();
		}
		return SUCCESS;
	}
	
	public String saveManufacturer() throws Exception {
		
		try {
			if(manufacturerEntity.getRandom()!=null && !"".equals(manufacturerEntity.getRandom())){
				ManufacturerEntity updateEntity = (ManufacturerEntity) commonDAO.findByField(ManufacturerEntity.class, "random", manufacturerEntity.getRandom()).iterator().next();
				updateEntity.setLxr(manufacturerEntity.getLxr());
				updateEntity.setTel(manufacturerEntity.getTel());
				updateEntity.setAddress(manufacturerEntity.getAddress());
				updateEntity.setStatus(ManufacturerEntity.STATUS_WAIT);
				commonDAO.update(updateEntity);
				msg = "更新成功";
			}else{
				Long currId = (Long) getSession().getAttribute("_USERID_");
				manufacturerEntity.setUserId(currId);
				manufacturerEntity.setRandom(createRandom());
				manufacturerEntity.setStatus(ManufacturerEntity.STATUS_WAIT);
				commonDAO.save(manufacturerEntity);
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
	public ManufacturerEntity getManufacturerEntity() {
		return manufacturerEntity;
	}
	public void setManufacturerEntity(ManufacturerEntity manufacturerEntity) {
		this.manufacturerEntity = manufacturerEntity;
	}

}
