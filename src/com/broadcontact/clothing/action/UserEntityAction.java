package com.broadcontact.clothing.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadcontact.clothing.entity.ManufacturerEntity;
import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.entity.UserLoginIpEntity;
import com.broadcontact.clothing.entity.UserNewsEntity;
import com.broadcontact.clothing.entity.supermarket.GoodsCartEntity;
import com.broadcontact.clothing.utils.PublicUtils;
import com.broadcontact.common.hibernate.CommonDAO;

@Component()
@Scope("prototype")
public class UserEntityAction extends BaseAction {

	@Resource
	private CommonDAO commonDAO;
	private Map<String, Object> map;
	private UserEntity userEntity;
	private String telCodeByUser;//用户自己填写的手机验证码
	private String emailCodeByUser;//用户自己填写的邮箱验证码
	private String imgCodeByUser;//用户自己填写的图片验证码
	private String registerType;//注册方式 0-->手机 1-->邮箱
	private String phoneLoginType;//手机登录方式 0-->手机验证码	 1-->密码
	private String losePwdType;//忘记密码选择方式 0-->手机验证码找回 1-->邮箱找回
	private String updatePwdType;//修改密码方式 0-->手机号修改 1-->账号修改
	private String bindType;//绑定手机和邮箱 0-->手机 1-->邮箱
	private File headFile;//上传头像
	private String paramStr;
	private String[] data;
	private File file;
	
	public String grzx() throws Exception{
		userEntity = getUserById();
		
		//工厂是否认证
		List<ManufacturerEntity>  list = commonDAO.findByField(ManufacturerEntity.class, "userId", userEntity.getId());
		
		String manufacturerIsSh = "NO";
		
		if(list.size()>1){
			msg = "系统错误";
			retVal = -1;
			return ERROR;
		}
		
		if(list.size()==1){
			ManufacturerEntity mfe = list.get(0);
			if(Long.valueOf(ManufacturerEntity.STATUS_PASS) == Long.valueOf(mfe.getStatus())){
				manufacturerIsSh = "YES";
			}
		}
		
		getRequest().setAttribute("manufacturerIsSh", manufacturerIsSh);
		
		return SUCCESS;
	}
	
	public String getBindsjAndyxInfo() throws Exception{
		return SUCCESS;
	}
	
	public String getXgmmInfo() throws Exception{
		return SUCCESS;
	}
	
	public String removeFile() throws Exception{
		
		userEntity = getCurrUser();
		
		userEntity.setYyzzImg(null);
		
		//每次删除后都需要改变userType为2（待审核）
		if(userEntity.getUserType()!=null){
			userEntity.setUserType("2");
		}
		
		commonDAO.update(userEntity);
		
		return SUCCESS;
	}
	
	/**
	 * 上传文件
	 */
	public String saveUploadFile() throws Exception{
		
		String fileParam = getRequest().getParameter("fileParam");
		String path;
		if("headImg".equals(fileParam)){
			path = ServletActionContext.getServletContext().getRealPath("/upfile/head");//资质文件
		}else{
			path = ServletActionContext.getServletContext().getRealPath("/upfile/zzwj");//资质文件
		}
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs(); 
		}
		String zzwjFileName = UUID.randomUUID()+".jpg";
		try {
			FileUtils.copyFile(file, new File(dir, zzwjFileName)); 
		} catch (Exception e) {
			msg = "资质文件上传失败";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		UserEntity currUser = getCurrUser();
		if("file".equals(fileParam)){
			currUser.setYyzzImg(zzwjFileName);
		}if("headImg".equals(fileParam)){
			currUser.setHeadImg(zzwjFileName);
		}
		
		//只针对完善资料中的资质文件 每次修改后都需要改变userType为2（待审核）
		if(!"headImg".equals(fileParam) && currUser.getUserType()!=null){
			currUser.setUserType("2");
		}
		
		commonDAO.update(currUser);
		
		msg = "上传成功";
		map = getJsonResult();
		return SUCCESS;
	}
	
	/**
	 * 个人中心完善资料
	 * @return
	 * @throws Exception
	 */
	public String saveWszl() throws Exception{
		
		UserEntity currUser = getCurrUser();
		currUser.setCompanyName(userEntity.getCompanyName());
		currUser.setAddress(userEntity.getAddress());
		currUser.setNsrsbh(userEntity.getNsrsbh());
		currUser.setLxr(userEntity.getLxr());
		currUser.setLxPhone(userEntity.getLxPhone());
		//currUser.setSwdjzName(userEntity.getSwdjzName());
		//currUser.setZzjgdmzName(userEntity.getZzjgdmzName()); 在上传文件action中保存
		String advantage = "";
		for(int i=0;i<data.length;i++){
			advantage+= data[i]+",";
		}
		currUser.setAdvantage(advantage);
		try {
			
			//每次修改资质文件后都需要改变userType为2（待审核）
			
			currUser.setUserType("2");
			
			commonDAO.update(currUser);
			msg = "保存成功";
		} catch (Exception e) {
			msg = "保存失败";
			retVal = -1;
		}
		
		map = getJsonResult();
		
		return SUCCESS;
	}
	
	
	public String getWszlInfo() throws Exception{
		userEntity = getUserById();
		
		if("3".equals(userEntity.getUserType())){ //审核不通过才需要该值
			String result = getNewsStr(userEntity,"1");
			getRequest().setAttribute("remarks", result);
		}
		
		
		return SUCCESS;
	}
	
	
	/**
	 * 上传头像
	 */
	public String uploadHeadImg() throws Exception{
		//保存的路径 
		String path = ServletActionContext.getServletContext().getRealPath("/upfile/head");//项目根路径下的upfile文件夹
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs(); 
		}
		String headFileName = UUID.randomUUID()+".jpg";
		try {
			FileUtils.copyFile(headFile, new File(dir, headFileName)); 
		} catch (Exception e) {
			msg = "头像上传失败";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		UserEntity currUser = getCurrUser();
		currUser.setHeadImg(headFileName);
		commonDAO.update(currUser);
		
		msg = "头像上传成功";
		map = getJsonResult(); 
		return SUCCESS;
	}
	
	
	/**
	 * 昵称修改
	 */
	public String updateUsername() throws Exception{
		UserEntity currUser = getCurrUser();
		String newUsername = userEntity.getUsername();
		boolean flag = true;
		if(newUsername.equals(currUser.getUsername())){
			flag = false;
			msg = "新昵称与旧昵称相同";
		}else{
			List<UserEntity> users = commonDAO.findByField(UserEntity.class, "username", newUsername);
			if(users.size()>0){
				flag = false;
				msg = "该昵称已经存在,修改失败";
			}
		}
		
		if(!flag){
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		currUser.setUsername(newUsername);
		commonDAO.update(currUser);
		
		msg = "成功修改昵称";
		map = getJsonResult();
		return SUCCESS;
	}
	
	/**
	 * 绑定邮箱/手机号(修改邮箱 和 手机号)
	 */
	public String updateEmailOrTel() throws Exception{
		UserEntity currUser = getCurrUser();
		Map<String, Object> resultMap;
		if("0".equals(bindType)){//绑定手机
			
			String newTel = userEntity.getTel();
			boolean f = true;
			
			if(newTel.equals(currUser.getTel())){
				f = false;
				msg = "新绑定手机与旧手机相同,修改失败";
			}else{
				//再次检验 新手机号之前是否绑定
				List<UserEntity> users = commonDAO.findByField(UserEntity.class, "tel", newTel);
				if(users.size()>0){
					f = false;
					msg = "新手机已经绑定其他账号,修改失败";
				}else{
					//验证图形验证码
					boolean imgcodeFlog = checkImgCodeBeforeCommit();
					if(!imgcodeFlog){
						f = false;
						msg = "图形验证码不正确";
					}else{
						resultMap = PublicUtils.checkTelCode(getSession(),telCodeByUser,userEntity.getTel());
						boolean flag = (boolean) resultMap.get("flag");
						if(!flag){
							f = false;
							msg = (String) resultMap.get("msg");
						}
					}
				}
			}
			
			if(!f){
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
			
			currUser.setTel(newTel);
			
		}else if("1".equals(bindType)){//绑定邮箱
			String newEmail = userEntity.getEmail();
			boolean f = true;
			if(newEmail.equals(currUser.getEmail())){ //邮箱没改变
				f = false;
				msg = "该邮箱你已经绑定";
			}else{
				//该新邮箱是否 以前绑定
				List<UserEntity> users = commonDAO.findByField(UserEntity.class, "email", newEmail);
				if(users.size()>0){
					f = false;
					msg = "新邮箱已经绑定其他账号,修改失败";
				}else{
					if(!userEntity.getPassword().equals(currUser.getPassword())){
						f = false;
						msg = "密码不正确";
					}else{
						//注册前还需要验证图片验证码
						boolean imgcodeFlog = checkImgCodeBeforeCommit();
						if(!imgcodeFlog){
							return SUCCESS;
						}else{
							//检测邮箱验证码
							resultMap = PublicUtils.checkEmail(getSession(), emailCodeByUser, userEntity.getEmail());
							boolean emailcodeFlog = (boolean) resultMap.get("flag");
							if(!emailcodeFlog){
								f = false;
								msg = (String) resultMap.get("msg");
							}
							
						}
						
					}
				}
			}
			
			if(!f){
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
			
			currUser.setEmail(newEmail);
		}
		
		msg = "绑定成功";
		map = getJsonResult();
		commonDAO.update(currUser);
		
		return SUCCESS;
	}
	
	
	/**
	 * 通过账户修改新密码
	 * 0.手机号修改
	 * 1.账号修改
	 */
	public String updateNewPassword() throws Exception{
		UserEntity currUser = getCurrUser(); //是否null 会在filter中进行
		String newPassword = getRequest().getParameter("newPassword");
		String password = currUser.getPassword();
		boolean resultFlag = true;
		if("1".equals(updatePwdType)){
			if(!password.equals(userEntity.getPassword())){ //原密码的比较
				msg = "原密码不正确,修改失败";
				resultFlag = false;
			}
			
			if(password.equals(newPassword)){ //原密码和新密码相同
				msg = "新密码跟旧密码相同,修改失败";
				resultFlag = false;
			}
		}else if("0".equals(updatePwdType)){
			Map<String, Object> resultMap = PublicUtils.checkTelCode(getSession(),telCodeByUser,userEntity.getTel());
			boolean flag = (boolean) resultMap.get("flag");
			if(!flag){ //验证码验证不通过
				msg = (String) resultMap.get("msg");
				resultFlag = false;
			}else{
				if(password.equals(newPassword)){
					msg = "新密码跟旧密码相同,修改失败";
					resultFlag = false;
				}
			}
		}
		
		if(!resultFlag){
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		currUser.setPassword(newPassword);//设置新密码
		commonDAO.update(currUser);
		
		msg = "密码修改成功";
		map = getJsonResult();
		
		destroyTelCode();
		
		return SUCCESS;
	}
	
	/**
	 * 忘记密码
	 * 1.前往修改页面
	 * 2.验证手机/邮箱
	 * 3.修改密码
	 */
	//1.前往修改页面(到第1步还是第2步的一个判断)
	public String toLostPwdPage() throws Exception{
		
		if(paramStr!=null && !"".equals(paramStr)){ //传了参数的需要验证是否参数有效
			Long id = Long.valueOf(paramStr.split("_")[0]);
			Long times = Long.valueOf(paramStr.split("_")[1]);
			
			checkLostPwdUrl(id, times);
			
			if("身份已经验证".equals(msg)){
				getRequest().setAttribute("verified", "yes");
				return SUCCESS;
			}
			
		}else{
			msg = "身份还未验证";
			retVal = -1;
		}
		
		//能读到这段代码 就表示没验证通过
		getRequest().setAttribute("verified", "no");
		return SUCCESS;
	}

	private void checkLostPwdUrl(Long id, Long times) throws Exception {
		UserEntity user = (UserEntity) commonDAO.selectById(UserEntity.class, id);
		boolean flag = true;
		if(user==null){ //参数第一关就没有过
			flag = false;
		}else{
			if(!times.toString().equals(user.getLostPwdTimes())){ //第二个参数不正确
				flag = false;
			}else{
				//链接正确的情况下 判断是否超时
				Long nowTimes = new Date().getTime();
				if(nowTimes-times >= 24*60*60*1000){ //大于1天
					msg = "该链接未能在规定时间进行修改,重新进行身份验证";
					retVal = -1;
				}else{
					//链接正确 且 没有超时
					msg = "身份已经验证";
				}
			}
		}
		if(!flag){
			msg = "链接不正确";
			retVal = -1;
		}
	}
	
	//2.步骤1的提交
	public String losePwdCheckTelOrEmail() throws Exception{
		
		List<UserEntity> users;
		if("0".equals(losePwdType)){//手机验证码找回
			//还需要验证图片验证码
			boolean f = checkImgCodeBeforeCommit();
			if(!f){
				return SUCCESS;
			}
			
			Map<String, Object> resultMap = PublicUtils.checkTelCode(getSession(),telCodeByUser,userEntity.getTel());
			boolean flag = (boolean) resultMap.get("flag");
			if(!flag){
				//手机验证码error
				retVal = -1;
				msg = (String) resultMap.get("msg");
				map = getJsonResult();
				return SUCCESS;
			}else{
				users = commonDAO.findByField(UserEntity.class, "tel", userEntity.getTel());
				if(users.size()==0){
					msg = "该手机号不存在,重新输入";
					retVal = -1;
					map = getJsonResult();
					return SUCCESS;
				}
			}
			
		}else{//邮箱找回
			String email = userEntity.getEmail();
			users = commonDAO.findByField(UserEntity.class, "email", email);
			if(users.size()==0){
				msg = "该邮箱不存在,重新输入";
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
		}
		
		Long currTimes = new Date().getTime();
		
		UserEntity user = users.get(0);
		list = new ArrayList<>();
		list.add(user.getId()+"_"+currTimes);
		msg = "验证成功";
		map = getJsonResult();
		
		if("1".equals(losePwdType)){//如果是邮箱验证 存入时间戳
			user.setLostPwdTimes(currTimes+"");
			commonDAO.update(user);
			
			destroyEmailCode();
			
		}else{
			destroyTelCode();
		}
		
		return SUCCESS;
	}
	//3.修改密码
	public String updatePassword() throws Exception {
		
		UserEntity updateUser = (UserEntity) commonDAO.selectById(UserEntity.class, userEntity.getId());
		
		//检测链接是否有效
		if("1".equals(losePwdType)){
			String lostPwdTimes = updateUser.getLostPwdTimes();
			checkLostPwdUrl(userEntity.getId(), Long.valueOf(lostPwdTimes));
			
			if(!"身份已经验证".equals(msg)){ //只有这个是链接验证成功的
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
		}
		
		if(updateUser.getPassword().equals(userEntity.getPassword())){
			retVal = -1;
			msg = "填写密码与原密码一致,修改失败";
			map = getJsonResult();
			return SUCCESS;
		}
		
		//身份验证成功 修改密码
		updateUser.setPassword(userEntity.getPassword());
		commonDAO.update(updateUser);
		
		msg = "修改成功";
		map = getJsonResult();
		
		return SUCCESS;
	}
	
	
	/**
	 * 注销
	 */
	public String logout() throws Exception{
		getSession().invalidate();
		//msg = "注销成功";
		//map = getJsonResult();
		return SUCCESS;
	}
	
	/**
	 * 登录(手机邮箱共用)
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception{
		String currAcc = userEntity.getTel();
		DetachedCriteria dc = DetachedCriteria.forClass(UserEntity.class);
		dc.add(Expression.or(Expression.eq("tel", userEntity.getTel()), Expression.eq("email", userEntity.getTel())));
		List<UserEntity> users = commonDAO.query(dc);
		if(users.size()<=0){
			msg = "账号不存在";
			retVal = -1;
		}else if(users.size()>1){//系统正常的情况下是不可能出现该情况的
			msg = "多个账号,系统错误,联系管理员";
			retVal =-1;
		}else if(users.size()==1){
			UserEntity loginUser = users.get(0);
			if(!loginUser.getPassword().equals(userEntity.getPassword())){
				msg = "密码不正确";
				retVal = -1;
			}else{
				msg = "登录成功";
				setLoginUserInfo(loginUser,currAcc);
				
				setCartSession(loginUser);		//购物车
				
				try {
					saveIp();
				} catch (Exception e) {
					msg = "保存IP出错";
					retVal = -1;
				}
				
			}
		}
		map = getJsonResult();
		return SUCCESS;
		
	}
	
	private void saveIp() throws Exception {
		UserLoginIpEntity ipEntity = new UserLoginIpEntity();
		ipEntity.setIp(PublicUtils.getIpAddress(getRequest()));
		ipEntity.setRandom(createRandom());
		ipEntity.setTime(new Date());
		ipEntity.setUserId(getCurrUser().getId());
		
		commonDAO.save(ipEntity);
	}

	private void setCartSession(UserEntity loginUser) throws Exception {
		Iterator<GoodsCartEntity> it = commonDAO.findByField(GoodsCartEntity.class, "user",loginUser).iterator();
		int num = 0;
		while(it.hasNext()){
			GoodsCartEntity cart = it.next();
			num += cart.getGoodsNum();
		}
		getSession().setAttribute("_CART_GOODS_NUM_", num);
	}
	 
	/**
	 * 邮箱登录
	 */
	public String loginByEmail() throws Exception{
		
		String email = userEntity.getEmail();
		List<UserEntity> users = commonDAO.findByField(UserEntity.class, "email", email);
		if(users.size()==0){
			msg = "该邮箱不存在,重新输入";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		//邮箱存在 
		UserEntity loginUser = users.get(0);
		String password = loginUser.getPassword();
		if(!password.equals(userEntity.getPassword())){
			//密码错误
			msg = "登录密码错误";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		setLoginUserInfo(loginUser,email);
		
		//验证成功 --> 登录
		msg = "登录成功";
		map = getJsonResult();
		
		return SUCCESS;
	}
	
	
	/**
	 * 手机登录
	 */
	public String loginByTel() throws Exception{
		
		//验证手机号是否存在
		List<UserEntity> users = commonDAO.findByField(UserEntity.class, "tel", userEntity.getTel());
		if(users.size()==0){
			msg = "该手机号码还没有注册";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		UserEntity loginUser = users.get(0);
		
		if("0".equals(phoneLoginType)){ //手机验证码登录
			//检测验证码
			Map<String, Object> resultMap = PublicUtils.checkTelCode(getSession(),telCodeByUser,userEntity.getTel());
			boolean flag = (boolean) resultMap.get("flag");
			if(!flag){
				retVal = -1;
				msg = (String) resultMap.get("msg");
				map = getJsonResult();
				return SUCCESS;
			}else{
				destroyTelCode();
			}
		}else{ //密码登录
			String password = loginUser.getPassword(); //数据库中密码
			
			if(!password.equals(userEntity.getPassword())){
				msg = "登录密码错误";
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
		}
		
		setLoginUserInfo(loginUser,userEntity.getTel());
		
		//验证成功 --> 登录
		msg = "登录成功";
		map = getJsonResult();
		
		return SUCCESS;
	}


	private void setLoginUserInfo(UserEntity loginUser,String acc) {
		getSession().setAttribute("_USER_", loginUser);
		getSession().setAttribute("_USERID_", loginUser.getId());
		
//		目前没有用户名 用登录账号代替(手机或者邮箱)
		getSession().setAttribute("_REALNAME_", loginUser.getTel());
	}
	
	public boolean checkImgCodeBeforeCommit(){
		Map<String, Object> resultMap = PublicUtils.checkImgCode(getSession(), imgCodeByUser);
		boolean f = (boolean) resultMap.get("flag");
		if(f==false){
			retVal = -1;
			msg = (String) resultMap.get("msg");
			map = getJsonResult();
		}
		return f;
	}
	
	/**
	 *	用户注册 手机注册
	 */
	public String addUser() throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//注册前还需要验证图片验证码
		boolean f = checkImgCodeBeforeCommit();
		if(!f){
			return SUCCESS;
		}
		
		
		if("0".equals(registerType)){
			//检测手机是否注册
			List<UserEntity> users = commonDAO.findByField(UserEntity.class, "tel", userEntity.getTel());
			if(users.size()>0){
				msg = "该手机号码已被注册";
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
			//检测手机验证码
			resultMap = PublicUtils.checkTelCode(getSession(),telCodeByUser,userEntity.getTel());
			
		}else if("1".equals(registerType)){
			List<UserEntity> users = commonDAO.findByField(UserEntity.class, "email", userEntity.getEmail());
			if(users.size()>0){
				msg = "该邮箱已被注册";
				retVal = -1;
				map = getJsonResult();
				return SUCCESS;
			}
			//检测邮箱验证码
			resultMap = PublicUtils.checkEmail(getSession(), emailCodeByUser, userEntity.getEmail());
		}
		boolean flag = (boolean) resultMap.get("flag");
		if(!flag){
			retVal = -1;
			msg = (String) resultMap.get("msg");
			map = getJsonResult();
			return SUCCESS;
		}
		
		try {
			userEntity.setRandom(createRandom());
			commonDAO.save(userEntity);
		} catch (Exception e) {
			msg = "注册失败";
			retVal = -1;
		}
		msg = "注册成功";
		map = getJsonResult();
		
		if("0".equals(registerType)){
			destroyTelCode();
		}else if("1".equals(registerType)){
			destroyEmailCode();
		}
		
		return SUCCESS;
	}

	//销毁手机验证码
	public void destroyTelCode(){
		getSession().removeAttribute("sendTelCodeTime");
		getSession().removeAttribute("telCode");
		getSession().removeAttribute("tel");
	}
	
	public void destroyEmailCode(){
		getSession().removeAttribute("email");
		getSession().removeAttribute("emailCode");
		getSession().removeAttribute("sendEmailCodeTime");
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
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	public String getTelCodeByUser() {
		return telCodeByUser;
	}
	public void setTelCodeByUser(String telCodeByUser) {
		this.telCodeByUser = telCodeByUser;
	}
	public String getPhoneLoginType() {
		return phoneLoginType;
	}
	public void setPhoneLoginType(String phoneLoginType) {
		this.phoneLoginType = phoneLoginType;
	}
	public String getLosePwdType() {
		return losePwdType;
	}
	public void setLosePwdType(String losePwdType) {
		this.losePwdType = losePwdType;
	}
	public String getUpdatePwdType() {
		return updatePwdType;
	}
	public void setUpdatePwdType(String updatePwdType) {
		this.updatePwdType = updatePwdType;
	}
	public String getBindType() {
		return bindType;
	}
	public void setBindType(String bindType) {
		this.bindType = bindType;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public File getHeadFile() {
		return headFile;
	}
	public void setHeadFile(File headFile) {
		this.headFile = headFile;
	}
	public String getEmailCodeByUser() {
		return emailCodeByUser;
	}
	public void setEmailCodeByUser(String emailCodeByUser) {
		this.emailCodeByUser = emailCodeByUser;
	}
	public String getImgCodeByUser() {
		return imgCodeByUser;
	}
	public void setImgCodeByUser(String imgCodeByUser) {
		this.imgCodeByUser = imgCodeByUser;
	}
	public String getParamStr() {
		return paramStr;
	}
	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public UserEntity getUserById() throws Exception{
		Long currUserId = (Long) getSession().getAttribute("_USERID_");
		
		return (UserEntity) commonDAO.selectById(UserEntity.class, currUserId);
	}
	
}
