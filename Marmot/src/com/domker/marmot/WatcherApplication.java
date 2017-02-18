/**
 * 
 */
package com.domker.marmot;

import android.app.Application;

import com.domker.marmot.config.ConfigManager;
import com.domker.marmot.core.Watcher;
import com.domker.marmot.net.WatcherNet;
import com.domker.marmot.push.PushManager;

/**
 * 全局的Application
 * 
 * @author wanlipeng
 * @date 2017年1月4日 下午7:13:30
 */
public class WatcherApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Watcher.initContext(this);
		WatcherNet.init(this);
		PushManager.getInstance(this);
		ConfigManager.init(this);
	}
	
}
