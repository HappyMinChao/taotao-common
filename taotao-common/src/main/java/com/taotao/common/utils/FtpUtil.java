package com.taotao.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param basePath FTP服务器基础目录,要在此目录上拼接时间目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放：/2016/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false 
	 * @author duminchao
	 */  
	public static boolean uploadFile(String host,int port, String username, String password, String basePath,
			String filePath, String filename, InputStream input){
		boolean result = false; //默认上传失败
		FTPClient client = new FTPClient();
		try {
			int reply;
			//连接FTP服务器，如果使用默认端口可以 connect(host)函数
			client.connect(host, port);
			//登录ftp服务器
			client.login(username, password);
			//在ftp连接后调用此方法用来判断连接是否成功
			reply = client.getReplyCode();
			//如果reply以2开头说明连接成功， 服务器活跃相应完成
			if(!FTPReply.isPositiveCompletion(reply)){
				//如果服务器没有相应,关闭连接,返回false
				client.disconnect();
				return result;
			}
			//连接成功， 切换到上传目录不成功， 目录不存在，没有权限等……
			if(!client.changeWorkingDirectory(basePath+filePath)){
				//如果目录不存在则创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				//循环创建目录
				for(String dir : dirs){
					//如果dir为空串或者null， 则直接跳过进入下一次循环
					if(null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					//一级一级的创建目录，并切换
					if(!client.changeWorkingDirectory(tempPath)){
						if(!client.makeDirectory(tempPath)){
							return result;
						}else{
							client.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//设置上传文件的二进制类型
			client.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件, 如果不成功则放回false;
			if(!client.storeFile(filename, input)){
				return result;
			}
			input.close();
			client.logout();
			result = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(client.isConnected()){
				try {
					client.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/** 
	 * Description: 从FTP服务器下载文件 
	 * @param host FTP服务器hostname 
	 * @param port FTP服务器端口 
	 * @param username FTP登录账号 
	 * @param password FTP登录密码 
	 * @param remotePath FTP服务器上的相对路径 
	 * @param fileName 要下载的文件名 
	 * @param localPath 下载后保存到本地的路径 
	 * @auth : duminchao 
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {  
	        FileInputStream in=new FileInputStream(new File("D:\\temp\\image\\gaigeming.jpg"));  
	        boolean flag = uploadFile("192.168.25.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images","/2015/01/21", "gaigeming.jpg", in);  
	        System.out.println(flag);  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	}
}
