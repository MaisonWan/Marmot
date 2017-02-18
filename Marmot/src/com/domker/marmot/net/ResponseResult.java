/**
 * 
 */
package com.domker.marmot.net;

import com.google.gson.annotations.SerializedName;

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
	@SerializedName("ErrorCode")
	public int errorCode = 0;
	
	@SerializedName("Data")
	public String data = null;

	@Override
	public String toString() {
		return "[errorCode=" + errorCode + ", data=" + data + "]";
	}

}
