/**
 * FtpClient.java
 * 2007-6-25
 * author: 胡文昊
 */
package org.hao.ftp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import sun.net.ftp.FtpClient;

/**
 * 一个简单的Ftp上传组件，如果想要使用配置文件设置前期操作，建议使用FtpClientConfigParse的objectFactory方法创建对象。
 * 
 * @author 胡文昊<br>
 * 
 * 创建时间：2007-6-25 下午03:32:18 <br>
 * 最后一次修改时间: 2007-6-25 下午03:32:18<br>
 */
/*
 * 
 */
public class SimpleFtpClient {

	/**
	 * Ftp客户端对象
	 */
	private FtpClient ftpClient;

	/**
	 * 主机地址
	 */
	private String host;

	/**
	 * 主机端口号
	 */
	private int port;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 返回主机地址
	 * @return 返回主机地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 * 设置主机地址
	 * @param host
	 *            要设置的主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 得到用户密码
	 * @return 返回用户密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户密码
	 * @param password
	 *            要设置的用户密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 返回端口号
	 * @return 返回端口号
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置端口号
	 * @param port
	 *            要设置的端口号
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 返回用户名
	 * @return 返回用户名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置用户名
	 * @param userName
	 *            要设置的用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 根据前面设置的信息，连接ftp服务器，将丢失前一次的连接
	 * @throws IOException
	 */
	public void connect() throws IOException {
		if (ftpClient != null && ftpClient.serverIsOpen())
			close();
		ftpClient = new FtpClient(host, port);
		ftpClient.login(userName, password);
		ftpClient.binary();
	}

	/**
	 * 根据传入的各项信息，连接ftp服务器，将丢失前一次的连接
	 * @param host
	 * 			主机地址
	 * @param port
	 * 			主机端口号
	 * @param userName
	 * 			用户名
	 * @param password
	 * 			密码
	 * @throws IOException
	 */
	public void connect(String host, int port, String userName, String password)
			throws IOException {
		this.host = host;
		this.port = port;
		this.userName = userName;
		this.password = password;

		connect();
	}

	/**
	 * 关闭当前连接
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (ftpClient != null && ftpClient.serverIsOpen()) {
			ftpClient.closeServer();
			ftpClient = null;
		}
	}

	/**
	 * 上传单个文件
	 * @param file
	 * 			要上传的文件对象
	 * @throws IOException
	 */
	public void upload(File file) throws IOException {
		if (ftpClient == null || !ftpClient.serverIsOpen())
			return;
		RandomAccessFile sendFile = new RandomAccessFile(file, "r");
		String fileName = file.getName();
		DataOutputStream out = new DataOutputStream(ftpClient.put(fileName));
		int ch;
		while (sendFile.getFilePointer() < sendFile.length()) {
			ch = sendFile.read();
			out.write(ch);
		}
		out.close();
		sendFile.close();
	}
	
	/**
	 * 上传单个文件
	 * @param fileName
	 * 			要上传的文件的文件名
	 * @throws IOException
	 */
	public void upload(String fileName) throws IOException {
		upload(new File(fileName));
	}
	
	/**
	 * 上传多个文件
	 * @param files
	 * 			要上传的多个文件对象数组
	 * @throws IOException
	 */
	public void upload(File []files) throws IOException {
		for (File file : files)
			upload(file);
	}
	
	/**
	 * 上传多个文件
	 * @param fileNames
	 * 			要上传的多个文件的文件名数组
	 * @throws IOException
	 */
	public void upload(String []fileNames) throws IOException {
		for (String fileName : fileNames) 
			upload(fileName);
	}
}
