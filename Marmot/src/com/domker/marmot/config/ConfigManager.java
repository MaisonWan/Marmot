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
		mEditor.putString(UID, uid);
		mEditor.commit();
	}
}
