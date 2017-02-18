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
	public static final String HOST = "http://192.168.1.107:8080/";

	public static final String API_VERSION = "v1/";
	/**
	 * 请求定位信息
	 */
	public static final String LOCATION_REGISTER = HOST + API_VERSION
			+ "location/report";
}
