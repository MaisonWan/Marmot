/**
 * 
 */
package com.domker.marmot.watcher.location;

import android.content.Context;

import com.domker.marmot.config.ConfigManager;
import com.domker.marmot.log.MLog;
import com.domker.marmot.watcher.AbstractWatcher;

/**
 * 位置监控类
 * 
 * @author Maison
 *
 */
public class LocationWatcher extends AbstractWatcher {
	private LocationHandler mHandler = null;
	
	/**
	 * @param context
	 */
	public LocationWatcher(Context context) {
		super(context);
		this.mHandler = new LocationHandler(context);
	}

	/* (non-Javadoc)
	 * @see com.system.marmot.watcher.AbstractWatcher#startWatcher()
	 */
	@Override
	public void startWatcher() {
		super.startWatcher();
	}

	/* (non-Javadoc)
	 * @see com.system.marmot.watcher.AbstractWatcher#stopWatcher()
	 */
	@Override
	public void stopWatcher() {
		super.stopWatcher();
		this.mHandler.removeMessages(LocationHandler.ACTION_UPDATE_LOCATION);
	}

	/* (non-Javadoc)
	 * @see com.system.marmot.watcher.AbstractWatcher#onStartExecute()
	 */
	@Override
	public void onStartExecute() {
		this.mHandler.removeMessages(LocationHandler.ACTION_UPDATE_LOCATION);
		// 间隔十五分钟
		long lastTime = ConfigManager.getInstance().getLocationTime();
		// 现在的时间距离间隔十五分钟还有多少毫秒
		long d = lastTime + LocationHandler.DEFAULT_DELAY_TIME - System.currentTimeMillis();
		if (d > 0) { // 大于0的时候，说明间隔时间还没到十五分钟
			MLog.i("LocationWatcher", "onStartExecute d = " + d);
			this.mHandler.sendEmptyMessageDelayed(LocationHandler.ACTION_UPDATE_LOCATION, d);
		} else {
			this.mHandler.sendEmptyMessage(LocationHandler.ACTION_UPDATE_LOCATION);
		}
	}

	
}
