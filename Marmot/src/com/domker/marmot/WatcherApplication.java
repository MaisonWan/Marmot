/**
 * 
 */
package com.domker.marmot;

import android.app.Application;

import com.domker.marmot.config.ConfigManager;
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
		PushManager.getInstance(this);
		ConfigManager.init(this);
	}
	
}
