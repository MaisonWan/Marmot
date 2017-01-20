package com.domker.marmot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.domker.marmot.core.Watcher;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UmengMessageDeviceConfig;

public class MainActivity extends Activity {
	private TextView mTextViewLog = null;
	private PushAgent mPushAgent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPushAgent = PushAgent.getInstance(this);
		mPushAgent.onAppStart();
		
		mTextViewLog = (TextView) this.findViewById(R.id.textViewLog);
		
		findViewById(R.id.buttonStartService).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Context context = MainActivity.this;
				Intent intent = Watcher.createIntent(context, WatcherService.class);
				Watcher.startService(context, intent);
			}
		});
		findViewById(R.id.buttonAppInfo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appInfo();
			}
		});
	}
	
	private void appInfo() {
		String pkgName = getApplicationContext().getPackageName();
		String info = String.format("DeviceToken:%s\n"
				+ "SdkVersion:%s\nAppVersionCode:%s\nAppVersionName:%s",
				mPushAgent.getRegistrationId(), MsgConstant.SDK_VERSION,
				UmengMessageDeviceConfig.getAppVersionCode(this),
				UmengMessageDeviceConfig.getAppVersionName(this));
		mTextViewLog.setText("应用包名:" + pkgName + "\n" + info);
	}
}
