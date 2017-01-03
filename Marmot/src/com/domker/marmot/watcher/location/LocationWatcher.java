/**
 * 
 */
package com.domker.marmot.watcher.location;

import android.content.Context;

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
		onStartExecute();
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
		this.mHandler.sendEmptyMessage(LocationHandler.ACTION_UPDATE_LOCATION);
	}

	
}
