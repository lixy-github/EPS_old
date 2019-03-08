package com.broadcontact.clothing.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadcontact.clothing.entity.UserEntity;
import com.broadcontact.clothing.utils.PublicUtils;
import com.broadcontact.common.hibernate.CommonDAO;
import com.todaynic.client.mobile.SMS;

@Component()
@Scope("prototype")
public class PublicAction extends BaseAction {
	
	@Resource
	private CommonDAO commonDAO;
	private Map<String, Object> map;
	private UserEntity userEntity;
	private String sendEmailType;//0-->忘记密码 1-->发送邮箱验证码
	private String imgCodeByUser;//用户填写的图片验证码
	
	
	public String getCodeImg() throws Exception{
		getResponse().setContentType("image/jpeg");
		HttpSession session = getSession();
		int width = 100;
		int height = 37;

		// 设置浏览器不要缓存此图片
		getResponse().setHeader("Pragma", "No-cache");
		getResponse().setHeader("Cache-Control", "no-cache");
		getResponse().setDateHeader("Expires", 0);

		// 创建内存图像并获得图形上下文
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		/*
		 * 产生随机验证码 定义验证码的字符表
		 */
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 36);
			rands[i] = chars.charAt(rand);
		}

		/*
		 * 产生图像 画背景
		 */
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);

		/*
		 * 随机产生120个干扰点
		 */

		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * width);
			int y = (int) (Math.random() * height);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

		// 在不同高度输出验证码的不同字符
		g.drawString("" + rands[0], 17, 24);
		g.drawString("" + rands[1], 33, 22);
		g.drawString("" + rands[2], 48, 25);
		g.drawString("" + rands[3], 64, 24);
		g.dispose();

		// 将图像传到客户端
		ServletOutputStream sos = getResponse().getOutputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", baos);
		byte[] buffer = baos.toByteArray();
		getResponse().setContentLength(buffer.length);
		sos.write(buffer);
		baos.close();
		sos.close();

		session.setAttribute("imgCode", new String(rands));
		
		return NONE;
	}
	
	
	/**
	 * 找回密码时 邮件发送 发送的是一个url 用户直接点击修改密码
	 * 而账号绑定邮箱时只验证邮箱是否合法 不进行发送邮件验证 ==>参考硬蛋
	 * 邮箱修改密码 提交后 有参数list(id_times) 
	 */
	public String sendEmail() throws Exception{
		
		Map<String, Object> resultMap = new HashMap<>();
		
		Long lastTime = (Long) getSession().getAttribute("sendEmailCodeTime");
		Long sendTelCodeTime = new Date().getTime(); 
		if( lastTime != null && (sendTelCodeTime-lastTime) < 1 *60 *1000  ){
			msg = "1分钟内不要重复请求";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		//先验证图片验证码
		if("1".equals(sendEmailType)){
			resultMap = PublicUtils.checkImgCode(getSession(), imgCodeByUser);
			boolean f = (boolean) resultMap.get("flag");
			if(f==false){
				retVal = -1;
				msg = (String) resultMap.get("msg");
				map = getJsonResult();
				return SUCCESS;
			}
		}
		
		String getEmailAddr = userEntity.getEmail();//收件人地址
		String param = getRequest().getParameter("param");
		//读取配置文件
		resultMap = PublicUtils.readConfig(getRequest(),"EmailConfig.ini"); 
		Properties configProp = (Properties) resultMap.get("prop");
		retVal = (int) resultMap.get("retVal");
		if(retVal==-1){
			msg = (String) resultMap.get("msg");
			map = getJsonResult();
			return SUCCESS;
		}
		String protocol = configProp.getProperty("mail.transport.protocol");
		String host = configProp.getProperty("mail.smtp.host");
		String auth = configProp.getProperty("mail.smtp.auth");
		String debug = configProp.getProperty("mail.debug");
		String sendEmailAccount = configProp.getProperty("sendEmailAccount"); //发送邮件账号
		String sendEmailPassword = configProp.getProperty("sendEmailPassword");//发送邮件密码
		
		try {
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", protocol);//协议
			prop.setProperty("mail.smtp.host", host);//主机名
			prop.setProperty("mail.smtp.auth", auth);//是否开启权限控制
			prop.setProperty("mail.debug", debug); //true则发送邮件时会打印发送时的信息
			//创建程序到邮件服务器之间的一次会话
			Session session = Session.getInstance(prop);
			//获取邮件对象
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sendEmailAccount));//发件人
			//BCC：暗送 CC：抄送
			message.setRecipients(RecipientType.TO, InternetAddress.parse(getEmailAddr));//如果都让接受 "xxx@qq.com,yyy@qq.com"
			
			String html = "";
			if("0".equals(sendEmailType)){
				//忘记密码发送链接
				String path = ServletActionContext.getServletContext().getRealPath("/htmlTemplate/lostPwdTempForEmail.html");
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy年MM月dd日");
				SimpleDateFormat sf2 = new SimpleDateFormat("HH时ss分");
				String nowTimeYmd = sf1.format(new Date());
				String nowTimeMs = sf2.format(new Date());
				
				message.setSubject("忘记密码");
				html = PublicUtils.readHtml(path);
				html = html.replaceAll("<mySpan1></mySpan1>", nowTimeYmd); //参数的传递用简单的替换方式处理
				html = html.replaceAll("<mySpan2></mySpan2>", nowTimeMs); 
				html = html.replaceAll("<mySpan3></mySpan3>", param); 
			}else if("1".equals(sendEmailType)){
				//邮箱注册等发送验证码
				String emailCode = String.valueOf((int)((Math.random()*9+1)*100000));//手机验证码
				message.setSubject("邮箱验证码");
				html = "邮箱验证码是："+emailCode+"<br/>十分钟之内有效";
				
				getSession().setAttribute("email", getEmailAddr);
				getSession().setAttribute("emailCode", emailCode);
				
			}
			getSession().setAttribute("sendEmailCodeTime", new Date().getTime());
			
			message.setContent(html, "text/html;charset=utf-8");
			
			Transport trans = session.getTransport();
			
			trans.connect(sendEmailAccount, sendEmailPassword); //账户 密码
			trans.sendMessage(message, message.getAllRecipients());
			trans.close();
		} catch (Exception e) {
			msg = "邮件发送失败";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		
		msg = "成功发送邮件";
		map = getJsonResult();
		
		return SUCCESS;
	}

	
	/**
	 *	获取手机验证码
	 */
	public String getTelCode() throws Exception{
		
		//先验证图片验证码
		Map<String, Object> resultMap = PublicUtils.checkImgCode(getSession(), imgCodeByUser);
		boolean f = (boolean) resultMap.get("flag");
		if(f==false){
			retVal = -1;
			msg = (String) resultMap.get("msg");
			map = getJsonResult();
			return SUCCESS;
		}
		
		Long lastTime = (Long) getSession().getAttribute("sendTelCodeTime");
		Long sendTelCodeTime = new Date().getTime(); 
		if( lastTime != null && (sendTelCodeTime-lastTime) < 1 *60 *1000  ){
			msg = "1分钟内不要重复获取验证码";
			retVal = -1;
			map = getJsonResult();
			return SUCCESS;
		}
		
		String telCode = String.valueOf((int)((Math.random()*9+1)*100000));//手机验证码
		System.out.println(telCode);
		
		resultMap = PublicUtils.readConfig(getRequest(),"VCPConfig.ini"); //读取配置文件
		Properties prop = (Properties) resultMap.get("prop");
		retVal = (int) resultMap.get("retVal");
		if(retVal==-1){
			msg = (String) resultMap.get("prop");
			map = getJsonResult();
			return SUCCESS;
		}
		Hashtable configTable = new Hashtable();
		configTable.put("VCPSERVER",prop.getProperty("VCPSERVER"));
		configTable.put("VCPSVPORT",prop.getProperty("VCPSVPORT"));
		configTable.put("VCPUSERID",prop.getProperty("VCPUSERID"));
		configTable.put("VCPPASSWD",prop.getProperty("VCPPASSWD"));
		
	    String PhoneNumber= userEntity.getTel();
	    String SendTime= "0";	 //即时发送	//request.getParameter("dtTime");
	    String MsgContent = "您的验证码是："+telCode+"，10分钟内有效";
	    String type= "3";    //通道选择: 0：默认通道； 2：通道2； 3：即时通道
	    SMS smssender=new SMS(configTable);
	    //System.out.println(telCode+"..."+checkTelTime);
	    getSession().setAttribute("sendTelCodeTime", sendTelCodeTime); //验证码发送的时间
		getSession().setAttribute("telCode", telCode);//发送的验证码
		getSession().setAttribute("tel", userEntity.getTel()); //接受验证码的手机
		
		//smssender.sendSMS(PhoneNumber,MsgContent,SendTime,type);
		
		msg = "成功发送验证码";
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

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getSendEmailType() {
		return sendEmailType;
	}

	public void setSendEmailType(String sendEmailType) {
		this.sendEmailType = sendEmailType;
	}


	public String getImgCodeByUser() {
		return imgCodeByUser;
	}


	public void setImgCodeByUser(String imgCodeByUser) {
		this.imgCodeByUser = imgCodeByUser;
	}

}
