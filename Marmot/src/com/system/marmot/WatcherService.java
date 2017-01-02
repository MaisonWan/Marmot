package com.system.marmot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.system.marmot.manager.WatcherManager;

/**
 * 监控服务
 * 
 * @author Maison
 *
 */
public class WatcherService extends Service {
	private WatcherManager watchManager = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		watchManager = new WatcherManager(this);
		watchManager.onStart();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		watchManager.onStartCommand();
		return START_STICKY;
	}

	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		watchManager.onStop();
	}
	
}
