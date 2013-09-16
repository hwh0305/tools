package org.hao.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 发送邮件类，将同一邮件内容发送给多个人，同一邮件中也可有多个附件<br>
 * 
 * @author 胡文昊<br>
 * 
 * 创建时间：2007-6-22 上午11:18:24<br> 
 * 最后一次修改时间: 2007-6-26 9:45<br>
 */
/*
 * 去除所有同步以及线程有关的部分，做成单纯的Bean，多线程发送将在其它类中实现 —— 胡文昊 2007-6-26 9:45
 */
public class SendMail implements java.io.Serializable {

	/**
	 * 生成的串行化版本序列号
	 */
	private static final long serialVersionUID = 6511076905638652121L;

	/**
	 * MIME邮件对象
	 */
	private MimeMessage mimeMsg;

	/**
	 * 邮件会话对象
	 */
	private Session session;

	/**
	 * 系统属性
	 */
	private Properties props;

	/**
	 * smtp服务器用户名
	 */
	private String userName;

	/**
	 * smtp服务器密码
	 */
	private String password;

	/**
	 * smtp服务器地址
	 */
	private String host;

	/**
	 * smtp服务器是否需要验证
	 */
	private boolean needAuth;

	/**
	 * 发送地址
	 */
	private String from;

	/**
	 * Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	 */
	private Multipart multipart;

	/**
	 * 构造一个默认的SendMail对象
	 */
	public SendMail() {
		props = System.getProperties();
		createMimeMessage();
	}

	/**
	 * 设置MIME信息，设置成功返回true
	 */
	private void createMimeMessage() {
		session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
		multipart = new MimeMultipart();
	}

	/**
	 * 得到smtp服务器地址
	 * 
	 * @return 返回smtp服务器地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置smtp服务器地址
	 * 
	 * @param host
	 *            要设置的smtp服务器地址
	 */
	public void setHost(String host) {
		props.put("mail.smtp.host", host); // 设置SMTP主机
	}

	/**
	 * 返回smtp服务器用户密码
	 * 
	 * @return 返回smtp服务器用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置smtp服务器用户密码
	 * 
	 * @param password
	 *            smtp服务器用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回smtp服务器要用到的用户名
	 * 
	 * @return 返回smtp服务器要用到的用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置smtp服务器的用户名
	 * 
	 * @param userName
	 *            要设置的smtp服务器的用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 返回smtp服务器是否需要身份验证
	 * 
	 * @return 如果需要返回true
	 */
	public boolean isNeedAuth() {
		return needAuth;
	}

	/**
	 * 设置smtp服务器是否需要身份验证
	 * 
	 * @param needAuth
	 *            设置smtp服务器是否需要身份验证
	 */
	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
		if (needAuth) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 返回发送电子邮件地址
	 * 
	 * @return 返回发送电子邮件地址
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * 设置发送电子邮件地址
	 * 
	 * @param from
	 *            要设置的发送电子邮件地址
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void setFrom(String from) throws AddressException,
			MessagingException {
		this.from = from;
		mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
	}

	/**
	 * 设置要发送邮件的主题
	 * 
	 * @param mailSubject
	 *            要发送邮件的主题
	 * @throws MessagingException 
	 */
	public void setTitle(String mailTitle) throws MessagingException {
		mimeMsg.setSubject(mailTitle);
	}

	/**
	 * 设置邮件内容，如果html参数为false，发送的是纯文本邮件
	 * 
	 * @param mailBody
	 *            设置邮件内容
	 * @param html
	 *            设置邮件内容是否为html格式
	 * @throws MessagingException
	 */
	public void setBody(String mailBody, boolean html)
			throws MessagingException {
		BodyPart bp = new MimeBodyPart();
		if (html)
			bp.setContent(mailBody, "text/html;charset=utf-8");
		else
			bp.setText(mailBody);
		multipart.addBodyPart(bp);
	}

	/**
	 * 设置邮件内容,默认html格式，字符集为utf-8
	 * 
	 * @param mailBody
	 *            设置邮件内容
	 * @throws MessagingException
	 */
	public void setBody(String mailBody) throws MessagingException {
		setBody(mailBody, true);
	}

	/**
	 * 设置邮件附件，可设置多个
	 * 
	 * @param filename
	 *            附件文件名（完整路径）
	 * @throws MessagingException
	 */
	public void addFileAffix(String filename)
			throws MessagingException {
		if (filename == null)
			return;
		addFileAffix(new FileDataSource(filename));
	}

	/**
	 * 设置邮件附件，可设置多个
	 * 
	 * @param file
	 *            附件文件
	 * @throws MessagingException
	 */
	public void addFileAffix(File file) throws MessagingException {
		if (file == null)
			return;
		addFileAffix(new FileDataSource(file));
	}

	/**
	 * 设置邮件附件，可设置多个
	 * 
	 * @param file
	 *            附件文件
	 * @throws MessagingException
	 */
	public void addFileAffix(FileDataSource file)
			throws MessagingException {
		BodyPart bp = new MimeBodyPart();
		bp.setDataHandler(new DataHandler(file));
		bp.setFileName(file.getName());

		multipart.addBodyPart(bp);
	}

	/**
	 * 设置收信人地址，可添加多个
	 * 
	 * @param to
	 *            收信人地址
	 * @throws MessagingException
	 */
	public void setTo(String[] to) throws MessagingException {
		if (to == null)
			return;
		InternetAddress[] sendTo = new InternetAddress[to.length];

		for (int i = 0; i < to.length; ++i)
			// 设置多个收件人
			sendTo[i] = new InternetAddress(to[i]);
		mimeMsg.setRecipients(Message.RecipientType.TO, sendTo);
	}

	/**
	 * 设置单个收信人地址
	 * 
	 * @param to
	 *            收信人地址
	 * @throws MessagingException
	 */
	public void setTo(String to) throws MessagingException {
		String[] toAdd = { to };
		setTo(toAdd);
	}

	/**
	 * 发送邮件<br>
	 * 如单线程发送，使用此方法
	 * @throws MessagingException 
	 */
	public void send() throws MessagingException {
			mimeMsg.setContent(multipart);
			mimeMsg.saveChanges();

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), userName,
					password);
			transport.sendMessage(mimeMsg, mimeMsg
					.getRecipients(Message.RecipientType.TO));

			transport.close();
	}
}