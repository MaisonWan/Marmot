/**
 * 
 */
package com.domker.marmot.net;


/**
 * 请求结果的返回值
 * 
 * @author wanlipeng
 * @date 2017年2月17日 下午3:05:59
 */
public class ResponseResult {
	/**
	 * 返回0的时候是正常，没有错误, 大于0有错误
	 */
	private int errorCode = 0;
	private String data = null;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
