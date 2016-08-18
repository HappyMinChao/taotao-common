package com.taotao.common.pojo;

/*
 * ��Ҫ���ص�json��ʽ�� ���ݴ˸�ʽ��Ƶ�pictureResult��
 * //�ɹ�ʱ
{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
}
//ʧ��ʱ
{
        "error" : 1,
        "message" : "������Ϣ"
}
 */
public class PictureResult {
	private int error;
	private String url;
	private String message;

	//���ó�˽����Ҫ����Ϊ �ɹ�ʱ �� ���� error : 0 , url ; ʧ��ʱ���� error : 1 , message;��ͬ���ܻ�����û�ʹ�ò����㣬 �������� success��fail����
	private PictureResult(int error, String url, String message) {
		super();
		this.error = error;
		this.url = url;
		this.message = message;
	}

	public static PictureResult success(String url) {
		return new PictureResult(0, url, null);
	}

	public static PictureResult faill(String message) {
		return new PictureResult(1, null, message);
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
