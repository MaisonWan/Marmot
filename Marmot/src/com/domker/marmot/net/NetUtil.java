package com.domker.marmot.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.domker.marmot.core.ExceptionPrinter;
import com.domker.marmot.core.Watcher;

/**
 * 网络工具类
 * 
 * @author wanlipeng
 * @date 2017年3月6日 下午3:40:30
 */
public class NetUtil {
	public static final int NETWORN_NONE = 0;
	public static final int NETWORN_WIFI = 1;
	public static final int NETWORN_MOBILE = 2;

	public static final int NETWORN_MOBILE_OTHER = 1;
	public static final int NETWORN_MOBILE_2G = 2;
	public static final int NETWORN_MOBILE_3G = 3;
	public static final int NETWORN_MOBILE_4G = 4;

	public static ConnectivityManager getNetManager(Context context) {
		return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/**
	 * 判断当前的网络连接类型（当前正在用什么上网）
	 * 
	 * @param context
	 * @return int
	 */
	public static int getNetworkType(Context context) {
		return getNetworkType(context, getNetManager(context));
	}

	/**
	 * 判断当前的网络连接类型（当前正在用什么上网）
	 * 
	 * @param context
	 * @param connManager
	 * @return int
	 */
	public static int getNetworkType(Context context, ConnectivityManager connManager) {
		try {
			NetworkInfo info = connManager.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				switch (info.getType()) {
				case ConnectivityManager.TYPE_WIFI:
					return NETWORN_WIFI;
				case ConnectivityManager.TYPE_MOBILE:
					return getNetMobileType(context, connManager);
				}
			}
		} catch (Exception e) {
			ExceptionPrinter.printStackTrace(e);
			return NETWORN_NONE;
		}
		return NETWORN_NONE;
	}

	/**
	 * 获取到当前网络类型名称
	 * 
	 * @return
	 */
	public static String getNetworkTypeName() {
		int type = getNetworkType(Watcher.getApplicationContext());
		switch (type) {
		case NETWORN_WIFI:
			return "WiFi";
		case NETWORN_MOBILE_2G:
			return "2G";
		case NETWORN_MOBILE_3G:
			return "3G";
		case NETWORN_MOBILE_4G:
			return "4G";
		default:
			return "unknown";
		}
	}
	
	/**
	 * 当前的数据连接类型（注意，只是avaliable，网络虽然是3G或者4G但当前联网的可能还是Wifi）
	 * 
	 * @param context
	 * @param connManager
	 * @return int
	 */
	public static int getNetMobileType(Context context, ConnectivityManager connManager) {
		NetworkInfo info = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (info != null && info.isAvailable()) {
			int networkType = info.getSubtype();
			switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_GPRS:
			case TelephonyManager.NETWORK_TYPE_EDGE:
			case TelephonyManager.NETWORK_TYPE_CDMA:
			case TelephonyManager.NETWORK_TYPE_1xRTT:
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return NETWORN_MOBILE_2G;
			case TelephonyManager.NETWORK_TYPE_UMTS:
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
			case TelephonyManager.NETWORK_TYPE_HSDPA:
			case TelephonyManager.NETWORK_TYPE_HSUPA:
			case TelephonyManager.NETWORK_TYPE_HSPA:
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
			case TelephonyManager.NETWORK_TYPE_EHRPD:
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return NETWORN_MOBILE_3G;
			case TelephonyManager.NETWORK_TYPE_LTE:
				return NETWORN_MOBILE_4G;
			default:
				return NETWORN_MOBILE_OTHER;
			}
		}
		return NETWORN_NONE;
	}

	/**
	 * 程序中访问http数据接口
	 */
	public static String getURLContent(String url, String charsetName) {
		URL u = null;
		BufferedReader in = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			u = new URL(url);
			in = new BufferedReader(new InputStreamReader(u.openStream(), charsetName));
			String str = null;
			while ((str = in.readLine()) != null) {
				stringBuffer.append(str);
			}
		} catch (Exception e) {
			ExceptionPrinter.printStackTrace(e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = stringBuffer.toString();
		return result;
	}

}
