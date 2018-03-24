package jin.lon.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件发送工具类
 * 
 * 1. javax.mail: 1.4.7+
 * 2. 邮箱配置文件(email.properties)放在src/main/resources下
 * 
 */
public class SendMailUtils {
	/**  
	 * sendMail:. <br/>  
	 *  
	 * @param subject 题目
	 * @param content 文本
	 * @param to      收件人
	 */
	public static void sendMail(String subject, String content, String to) {
	    //创建文档
		Properties pps = new Properties();
		//使用类加载器加载SRC/下的配置文件
		InputStream email = SendMailUtils.class.getResourceAsStream("/email.properties");
		//如果没加载到文件
		if (null == email) {
			System.err.println("邮箱配置文件(email.properties)不存在!");
			return;
		}
		try {
		    //文档读取文件
			pps.load(email);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//从文档中获得发件地址
		String address = pps.getProperty("address"); // yhmpc@163.com
		//从文档中获得密码
		String password = pps.getProperty("password");
		String smtp_host = "smtp." + address.replaceAll(".*@(.*)", "$1"); //smtp.163.com

		Properties props = new Properties();
		//设置发出邮件方的smtp协议
		props.setProperty("mail.smtp.host", smtp_host);
		//设置发出邮件方的传输协议
		props.setProperty("mail.transport.protocol", "smtp");
		//设置协议授权
		props.setProperty("mail.smtp.auth", "true");
		
		//如果是qq的发送站
		if ("smtp.qq.com".equals(smtp_host)) {
			MailSSLSocketFactory sslFactory = null;
			try {
				sslFactory = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			//信任发射站
			sslFactory.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.socketFactory", sslFactory);
			props.put("mail.smtp.ssl.enable", "true");
		}

		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(address));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, address, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}

}
