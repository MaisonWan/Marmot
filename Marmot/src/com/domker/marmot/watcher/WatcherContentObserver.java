/**
 * 
 */
package com.domker.marmot.watcher;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

/**
 * ContentProvier监控的抽象基类
 * 
 * @author wanlipeng
 * @date 2017年1月3日
 */
public abstract class WatcherContentObserver extends ContentObserver {
	protected Context mContext = null;
	
	/**
	 * @param handler
	 */
	public WatcherContentObserver(Context context, Handler handler) {
		super(handler);
		this.mContext = context;
	}
	
	/**
	 * 检测是否发生改变
	 */
	public abstract void checkChange();
}
