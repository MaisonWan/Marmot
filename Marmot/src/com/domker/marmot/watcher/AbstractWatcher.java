/**
 * 
 */
package com.domker.marmot.watcher;

import android.content.Context;

/**
 * 抽象监控基类
 * 
 * @author Maison
 *
 */
public abstract class AbstractWatcher {
	protected Context mContext = null;
	/**
	 * 是否在运行
	 */
	protected boolean isRunning = false;
	
	public AbstractWatcher(Context context) {
		this.mContext = context;
	}

	/**
	 * 开始服务
	 */
	public void startWatcher() {
		this.isRunning = true;
	}

	/**
	 * 停止服务
	 */
	public void stopWatcher() {
		this.isRunning = false;
	}
	
	/**
	 * 是否在监视
	 * 
	 * @return
	 */
	public boolean isWatching() {
		return isRunning;
	}

	/**
	 * 开始执行业务，刷新重新计时执行。
	 */
	public abstract void onStartExecute();
}
