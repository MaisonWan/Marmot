/**
 * 
 */
package com.domker.marmot.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置管理中心
 * 
 * @author wanlipeng
 * @date 2017年1月4日 下午6:12:56
 */
public final class ConfigManager implements ConfigConst {
	private static ConfigManager manager = null;
	
	private SharedPreferences.Editor mEditor = null;
	private SharedPreferences mSharedPref = null;
	
	/**
	 * 需要最先调用这个方法，建议在Application中
	 * 
	 * @param context 建议传递ApplicationContext
	 */
	public static void init(Context context) {
		if (manager == null) {
			manager = new ConfigManager(context);
		}
	}
	
	public static ConfigManager getInstance() {
		if (manager == null) {
			throw new RuntimeException("ConfigManager do not init.");
		}
		return manager;
	}
	
	private ConfigManager(Context context) {
		int mode = Context.MODE_PRIVATE;
		mSharedPref = context.getSharedPreferences("marmot", mode);
		mEditor = mSharedPref.edit();
	}
	
	/**
	 * 获取唯一标识
	 * @return
	 */
	public String getUid() {
		return mSharedPref.getString(UID, UID_DEFAULT);
	}
	
	/**
	 * 设置保存唯一ID
	 * 
	 * @param uid
	 */
	public void setUid(String uid) {
		setValue(UID, uid);
	}
	
	/**
	 * 获取短信上传的时间
	 * @return
	 */
	public long getSmsTime() {
		return mSharedPref.getLong(SMS_TIME_LINE, SMS_TIME_LINE_DEFAULT);
	}
	
	/**
	 * 设置短信时间点，保证这个时间点之前的短信都上传完毕
	 * @param smsTime
	 */
	public void setSmsTime(long smsTime) {
		setValue(SMS_TIME_LINE, smsTime);
	}
	
	/**
	 * 获取通话上传的时间
	 * @return
	 */
	public long getCallTimeline() {
		return mSharedPref.getLong(CALL_TIME_LINE, CALL_TIME_LINE_DEFAULT);
	}
	
	/**
	 * 设置通话时间点，保证这个时间点之前的通话都上传完毕
	 * @param smsTime
	 */
	public void setCallTimeline(long callTime) {
		setValue(CALL_TIME_LINE, callTime);
	}
	
	/**
	 * 获取上次发送位置信息时间
	 * @return
	 */
	public long getLocationTime() {
		return mSharedPref.getLong(LOCATION_TIME_LINE, LOCATION_TIME_LINE_DEFAULT);
	}
	
	/**
	 * 设置保存发送位置信息的时间点
	 * 
	 * @param time
	 */
	public void setLocationTime(long time) {
		setValue(LOCATION_TIME_LINE, time);
	}
	
	/**
	 * 获取设备信息上报的时间点
	 * @return
	 */
	public long getDeviceTime() {
		return mSharedPref.getLong(DEVICE_TIME_LINE, DEVICE_TIME_LINE_DEFAULT);
	}
	
	/**
	 * 设置设备信息上报的时间点
	 * 
	 * @param time
	 */
	public void setDeviceTime(long time) {
		setValue(DEVICE_TIME_LINE, time);
	}
	
	private void setValue(String key, long value) {
		mEditor.putLong(key, value);
		mEditor.commit();
	}
	
	private void setValue(String key, String value) {
		mEditor.putString(key, value);
		mEditor.commit();
	}
}
