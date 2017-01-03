package com.domker.marmot.manager;

import android.content.Context;

import com.domker.marmot.watcher.location.LocationWatcher;
import com.domker.marmot.watcher.sms.SmsWatcher;

/**
 * 检测服务管理器
 * 
 * @author Maison
 */
public class WatcherManager {
	private Context mContext = null;
	
	private LocationWatcher locationWatcher = null;
	private SmsWatcher smsWathcer = null;
	
	public WatcherManager(Context context) {
		this.mContext = context;
		locationWatcher = new LocationWatcher(mContext);
		smsWathcer = new SmsWatcher(mContext);
	}

	/**
	 * 开始监控
	 */
	public void onStart() {
		locationWatcher.startWatcher();
		smsWathcer.startWatcher();
	}
	
	/**
	 * 立刻执行
	 */
	public void onStartCommand() {
		locationWatcher.onStartExecute();
		smsWathcer.onStartExecute();
	}
	
	/**
	 * 停止监控
	 */
	public void onStop() {
		locationWatcher.stopWatcher();
		smsWathcer.stopWatcher();
	}
}
