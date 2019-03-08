package com.broadcontact.common.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

  public class EmailAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication (userName,pwd);
    }
  }
  
  private String smtpServer = "mail.nbcareer.com";
  private String defaultFrom = "webposter@nbcareer.com";
  private boolean needAuth = true;
  private String userName = "webposter@nbcareer.com";
  private String pwd = "1$r%gs8#";
  
  public boolean getNeedAuth() {
    return needAuth;
  }

  public void setNeedAuth(boolean needAuth) {
    this.needAuth = needAuth;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getDefaultFrom() {
    return defaultFrom;
  }

  public void setDefaultFrom(String defaultFrom) {
    this.defaultFrom = defaultFrom;
  }

  public String getSmtpServer() {
    return smtpServer;
  }

  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }

  public boolean send(String from, String to, String subject, String text) {
    Properties props = new Properties();
    Session sendMailSession;
    Transport transport; 
    try {
      if(needAuth){
        props.put("mail.smtp.host",smtpServer);
        props.put("mail.smtp.auth", "true");
        sendMailSession =Session.getInstance(props,new EmailAuthenticator());
      }else{
        props.put("mail.smtp.host",smtpServer);
        sendMailSession =Session.getInstance(props);
      }
      Message newMessage = new MimeMessage(sendMailSession);
      if(from==null)
        newMessage.setFrom(new InternetAddress(defaultFrom));
      else
        newMessage.setFrom(new InternetAddress(from));
      newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress (to));
      newMessage.setSubject(subject);
      newMessage.setSentDate(new Date());
      newMessage.setText(text);
      transport = sendMailSession.getTransport("smtp");
      transport.send(newMessage);
      transport.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }


  public MailSender() {
	super();
  }

  public MailSender(String smtpServer, String defaultFrom, boolean needAuth,
		String userName, String pwd) {
	this.smtpServer = smtpServer;
	this.defaultFrom = defaultFrom;
	this.needAuth = needAuth;
	this.userName = userName;
	this.pwd = pwd;
  }

}
