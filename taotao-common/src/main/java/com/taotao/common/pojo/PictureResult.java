package com.taotao.common.pojo;

/*
 * 需要返回的json格式， 根据此格式设计的pictureResult类
 * //成功时
{
        "error" : 0,
        "url" : "http://www.example.com/path/to/file.ext"
}
//失败时
{
        "error" : 1,
        "message" : "错误信息"
}
 */
public class PictureResult {
	private int error;
	private String url;
	private String message;

	//设置成私有主要是因为 成功时 ， 设置 error : 0 , url ; 失败时设置 error : 1 , message;不同可能会造成用户使用不方便， 所有有了 success和fail方法
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
