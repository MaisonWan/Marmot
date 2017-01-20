package com.domker.marmot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.domker.marmot.device.DeviceManager;
import com.domker.marmot.manager.WatcherManager;
import com.domker.marmot.push.PushManager;

/**
 * 监控服务
 * 
 * @author Maison
 *
 */
public class WatcherService extends Service {
	private DeviceManager deviceManager = null;
	private WatcherManager watchManager = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.getInstance(getApplicationContext()).pushService();
		deviceManager = new DeviceManager(this);
		watchManager = new WatcherManager(this);
		watchManager.onStart();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		deviceManager.checkList();
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
