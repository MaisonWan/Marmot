package com.domker.marmot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.domker.marmot.audio.RecordManager;
import com.domker.marmot.device.DeviceManager;
import com.domker.marmot.log.MLog;
import com.domker.marmot.manager.WatcherManager;
import com.domker.marmot.push.PushManager;
import com.domker.marmot.push.command.CommandParser;
import com.domker.marmot.push.command.PushCommand;

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
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		deviceManager.checkList();
		watchManager.onStartCommand();
//		recordManager.recordAudio();
		String json = "{\"type\":3,\"time\":123456,\"data\":{\"before\":123,\"after\":456}}";
		PushCommand cmd = CommandParser.parser(json);
		MLog.i(cmd.toString());
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
