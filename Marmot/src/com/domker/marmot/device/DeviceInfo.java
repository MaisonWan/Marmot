/**
 * 
 */
package com.domker.marmot.device;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 设备信息
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午7:21:00
 */
public final class DeviceInfo {
	private Context mContext = null;
	private TelephonyManager tm = null;
	
	public DeviceInfo(Context context) {
		this.mContext = context;
		tm = getTelephonyManager(context);
	}

	/**
	 * 得到手机电话号码
	 * 
	 * @param context
	 * @return
	 */
	public String getPhoneNumber() {
		switch (tm.getSimState()) { // getSimState()取得sim的状态 有下面6中状态
		case TelephonyManager.SIM_STATE_ABSENT:
			return "无卡";
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			return "需要NetworkPIN解锁";
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			return "需要PIN解锁";
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			return "需要PUK解锁";
		case TelephonyManager.SIM_STATE_READY:
			return tm.getLine1Number();
		default:
			return "Unknown";
		}
	}
	
	/**
	 * 返回IMEI，如果不可用，可能返回null
	 * 
	 * @param context
	 * @return
	 */
	public String getPhoneImei() {
		return tm.getDeviceId();
	}
	
	/**
	 * 命令行下能显示的序列号
	 * 
	 * @return
	 */
	public String getSerial() {
		return Build.SERIAL;
	}
	
	/**
	 * 得到AndroidId，每次恢复出厂设置时，随机产生。
	 * 
	 * @return
	 */
	public String getAndroidID() {
		String androidID = android.provider.Settings.Secure.getString(
				mContext.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return androidID;
	}

	/**
	 * 返回IMSI
	 * 
	 * @return 如果不可用返回null
	 */
	public String getIMSI() {
		return tm.getSubscriberId();
	}
	
	/**
	 * 获取屏幕真实尺寸
	 * 
	 * @return
	 */
	public Size getScreenRealSize() {
		WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Point p = new Point();
		manager.getDefaultDisplay().getRealSize(p);
		return new Size(p.x, p.y);
	}
	
	/**
	 * 每英寸像素点数
	 * 
	 * @return
	 */
	public int getDensityDpi() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}
	
	/**
	 * 标准像素密度(160)的倍数
	 * 
	 * @return
	 */
	public float getDensity() {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		return dm.density;
	}
	
	/**
	 * 系统sdk版本号
	 * 
	 * @return 数字版本号
	 */
	public int getSdkVersion() {
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * 总内存
	 * 
	 * @return 返回KB或者MB，GB
	 */
	public String getTotalMemory() {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String arrayOfString = null;
		long initial_memory = 0;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
			Matcher matcher = Pattern.compile("\\d+").matcher(str2);
			if (matcher.find()) {
				arrayOfString = matcher.group();
			}
			initial_memory = Long.valueOf(arrayOfString).longValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Formatter.formatFileSize(mContext, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}
	
	private TelephonyManager getTelephonyManager(Context context) {
		return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
}
