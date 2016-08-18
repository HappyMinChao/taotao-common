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
	 * Description: ��FTP�������ϴ��ļ� 
	 * @param host FTP������hostname 
	 * @param port FTP�������˿� 
	 * @param username FTP��¼�˺� 
	 * @param password FTP��¼���� 
	 * @param basePath FTP����������Ŀ¼,Ҫ�ڴ�Ŀ¼��ƴ��ʱ��Ŀ¼
	 * @param filePath FTP�������ļ����·������������ڴ�ţ�/2016/01/01���ļ���·��ΪbasePath+filePath
	 * @param filename �ϴ���FTP�������ϵ��ļ��� 
	 * @param input ������ 
	 * @return �ɹ�����true�����򷵻�false 
	 * @author duminchao
	 */  
	public static boolean uploadFile(String host,int port, String username, String password, String basePath,
			String filePath, String filename, InputStream input){
		boolean result = false; //Ĭ���ϴ�ʧ��
		FTPClient client = new FTPClient();
		try {
			int reply;
			//����FTP�����������ʹ��Ĭ�϶˿ڿ��� connect(host)����
			client.connect(host, port);
			//��¼ftp������
			client.login(username, password);
			//��ftp���Ӻ���ô˷��������ж������Ƿ�ɹ�
			reply = client.getReplyCode();
			//���reply��2��ͷ˵�����ӳɹ��� ��������Ծ��Ӧ���
			if(!FTPReply.isPositiveCompletion(reply)){
				//���������û����Ӧ,�ر�����,����false
				client.disconnect();
				return result;
			}
			//���ӳɹ��� �л����ϴ�Ŀ¼���ɹ��� Ŀ¼�����ڣ�û��Ȩ�޵ȡ���
			if(!client.changeWorkingDirectory(basePath+filePath)){
				//���Ŀ¼�������򴴽�Ŀ¼
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				//ѭ������Ŀ¼
				for(String dir : dirs){
					//���dirΪ�մ�����null�� ��ֱ������������һ��ѭ��
					if(null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					//һ��һ���Ĵ���Ŀ¼�����л�
					if(!client.changeWorkingDirectory(tempPath)){
						if(!client.makeDirectory(tempPath)){
							return result;
						}else{
							client.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			//�����ϴ��ļ��Ķ���������
			client.setFileType(FTP.BINARY_FILE_TYPE);
			//�ϴ��ļ�, ������ɹ���Ż�false;
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
	 * Description: ��FTP�����������ļ� 
	 * @param host FTP������hostname 
	 * @param port FTP�������˿� 
	 * @param username FTP��¼�˺� 
	 * @param password FTP��¼���� 
	 * @param remotePath FTP�������ϵ����·�� 
	 * @param fileName Ҫ���ص��ļ��� 
	 * @param localPath ���غ󱣴浽���ص�·�� 
	 * @auth : duminchao 
	 */  
	public static boolean downloadFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			// �������Ĭ�϶˿ڣ�����ʹ��ftp.connect(host)�ķ�ʽֱ������FTP������
			ftp.login(username, password);// ��¼
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			ftp.changeWorkingDirectory(remotePath);// ת�Ƶ�FTP������Ŀ¼
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
