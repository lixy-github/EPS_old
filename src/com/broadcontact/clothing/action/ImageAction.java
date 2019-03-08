package com.broadcontact.clothing.action;

import java.io.File;
import java.io.IOException;
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
public class ImageAction extends BaseAction {	
	private File file;
	private String fileContentType;
	private Map map = new HashMap<>();
	private List list = new ArrayList<>();
	private String savaFileName;
	
	
	public String upLoadImage() throws IOException{
		String dateStr = Tools.processDate(new Date());
		String name = String.valueOf(createRandom()+"."+fileContentType.split("/")[1]);
		if(file != null){
			String filePath = ServletActionContext.getServletContext().getRealPath("/upload/"+savaFileName+"/"+dateStr);
			File dirFile = new File(filePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			} 
            FileUtils.copyFile(getFile(), new File(filePath,name));				//原图
            map.put("path","/upload/"+savaFileName+"/"+dateStr+"/"+name);
		}	
		return SUCCESS;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getSavaFileName() {
		return savaFileName;
	}

	public void setSavaFileName(String savaFileName) {
		this.savaFileName = savaFileName;
	}	
}
