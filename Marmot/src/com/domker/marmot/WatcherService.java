package com.domker.marmot;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.domker.marmot.audio.RecordManager;
import com.domker.marmot.core.ExceptionPrinter;
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
	private RecordManager recordManager = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		PushManager.getInstance(getApplicationContext()).pushService();
		deviceManager = new DeviceManager(this);
		watchManager = new WatcherManager(this);
		watchManager.onStart();
		recordManager = new RecordManager(this);
		startForeground();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		deviceManager.checkList();
		watchManager.onStartCommand();
//		recordManager.recordAudio();
//		String json = "{\"type\":3,\"time\":123456,\"data\":{\"before\":123,\"after\":456}}";
//		PushCommand cmd = CommandParser.parser(json);
//		MLog.i(cmd.toString());
		return START_STICKY;
	}
	
	/**
	 * 启动保护通知 绑定Notification提高Service优先级 防止被系统回收
	 */
	private void startForeground() {
		try {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), WatcherService.class);
			PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
			Notification notification = new Notification.Builder(this)
					.setTicker("WatcherService is Running")
					.setContentIntent(pendingIntent)
					.setWhen(System.currentTimeMillis()).build();
			startForeground(0, notification);
		} catch (Exception e) {
			ExceptionPrinter.printStackTrace(e);
		}
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
