/**
 * 
 */
package com.domker.marmot;

import com.domker.marmot.config.ConfigManager;

import android.app.Application;

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
		ConfigManager.init(this);
	}
	
}
