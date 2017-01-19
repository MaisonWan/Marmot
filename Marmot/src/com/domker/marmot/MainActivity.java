package com.domker.marmot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.domker.marmot.core.Watcher;
import com.umeng.message.PushAgent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.buttonStartService).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Context context = MainActivity.this;
				Intent intent = Watcher.createIntent(context, WatcherService.class);
				Watcher.startService(context, intent);
			}
		});
		PushAgent.getInstance(this).onAppStart();
	}
}
