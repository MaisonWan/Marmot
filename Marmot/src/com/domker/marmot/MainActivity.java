package com.domker.marmot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.domker.marmot.core.Watcher;
import com.umeng.message.PushAgent;

public class MainActivity extends Activity implements OnClickListener {
	private TextView mTextViewLog = null;
	private PushAgent mPushAgent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.onAppStart();
		
		mTextViewLog = (TextView) this.findViewById(R.id.textViewLog);
		
		findViewById(R.id.buttonStartService).setOnClickListener(this);
		findViewById(R.id.buttonLogInfo).setOnClickListener(this);
		findViewById(R.id.buttonLogClear).setOnClickListener(this);
		findViewById(R.id.buttonProcessInfo).setOnClickListener(this);
	}
	
//	private void appInfo() {
//		String pkgName = getApplicationContext().getPackageName();
//		String info = String.format("DeviceToken:%s\n"
//				+ "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
//				mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
//				UmengMessageDeviceConfig.getAppVersionCode(this),
//				UmengMessageDeviceConfig.getAppVersionName(this));
//		mTextViewLog.setText("应用包名:" + pkgName + "\n" + info);
//	}
	
	private String listProcess() {
		StringBuffer names = new StringBuffer();
		ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningAppProcessInfo p : am.getRunningAppProcesses()) {
			if (p.processName.contains("com.domker")) {
				names.append(p.processName).append(" ");
				names.append(p.importance).append(" ");
				names.append(p.pid).append(" ");
				names.append("\n");
			}
		}
		return names.toString();
	}

	private void startWatcherService() {
		final Context context = MainActivity.this;
		Intent intent = Watcher.createIntent(context, WatcherService.class);
		Watcher.startService(context, intent);
		String names = listProcess();
		mTextViewLog.setText(names);
	}
	
	private String readLog() {
		FileReader freader = null;
		BufferedReader reader = null;
		try {
			StringBuffer buffer = new StringBuffer();
			freader = new FileReader(Watcher.getSdcardPath() + "/Marmot/Log/marmot.log");
			reader = new BufferedReader(freader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line).append("\n");
			}
			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "log empty";
	}
	
	private void deleteLog() {
		String path = Watcher.getSdcardPath() + "/Marmot/Log/marmot.log";
		File file = new File(path);
		if (file.delete()) {
			Toast.makeText(this, "Clear Log Succeed", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Clear Log Error", Toast.LENGTH_SHORT).show();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonStartService:
			startWatcherService();
			break;
		case R.id.buttonLogInfo:
			mTextViewLog.setText(readLog());
			break;
		case R.id.buttonLogClear:
			deleteLog();
			break;
		case R.id.buttonProcessInfo:
			String names = listProcess();
			mTextViewLog.setText(names);
			break;
		}
	}

}
