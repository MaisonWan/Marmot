/**
 * 
 */
package com.domker.marmot.net;

/**
 * 网络连接地址
 * 
 * @author Maison
 *
 */
public final class Urls {
//	public static final String HOST = "http://192.168.1.107:8080/";
	public static final String HOST = "http://www.domker.com:8080/";

	public static final String API_VERSION = "v1/";

	/**
	 * 请求定位信息
	 */
	public static final String LOCATION_REPORT = HOST + API_VERSION + "location/report";

	/**
	 * 注册设备信息
	 */
	public static final String DEVICE_REGISTER = HOST + API_VERSION + "device/register";
	
	/**
	 * 上报短信
	 */
	public static final String SMS_REPORT = HOST + API_VERSION + "sms/report";
	
	/**
	 * 上报通话记录
	 */
	public static final String CALL_REPORT = HOST + API_VERSION + "call/report";
}
