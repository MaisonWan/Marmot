/**
 * 
 */
package com.domker.marmot.device;

import android.content.Context;
import android.text.TextUtils;

import com.domker.marmot.config.ConfigManager;

/**
 * 设备管理器，初始化一些标识等
 * 
 * @author wanlipeng
 * @date 2017年1月4日 下午7:15:12
 */
public final class DeviceManager {
	private Context mContext = null;
	private ConfigManager mConfigManager = null;
	private DeviceInfo mDeviceInfo = null;
	
	public DeviceManager(Context context) {
		this.mContext = context;
		mDeviceInfo = new DeviceInfo(context);
		mConfigManager = ConfigManager.getInstance();
	}
	
	/**
	 * 初始化检测，决定初始化和是否执行
	 */
	public void checkList() {
		if (!checkUid()) {
			// 执行初始化的一些操作
		}
	}
	
	/**
	 * 检测Uid是否存在，不存在则生成一个
	 * 
	 * @return 如果返回false，则不合法，生成一个新的uid，完成初始化的一系列操作
	 */
	private boolean checkUid() {
		String uid = mConfigManager.getUid();
		if (TextUtils.isEmpty(uid) || !mDeviceInfo.isCorrectUid(uid)) {
			uid = mDeviceInfo.createUid();
			mConfigManager.setUid(uid);
			return false;
		}
		return true;
	}
}
