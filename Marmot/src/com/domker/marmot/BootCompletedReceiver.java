/**
 * 
 */
package com.domker.marmot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;

import com.domker.marmot.core.AsyncHandler;
import com.domker.marmot.core.CpuWakeLock;
import com.domker.marmot.core.Watcher;

/**
 * 系统启动之后的广播
 * 
 * @author wanlipeng
 * @date 2017年1月5日 下午5:49:41
 */
public class BootCompletedReceiver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {
		final PendingResult result = goAsync();
		final WakeLock wl = CpuWakeLock.createPartialWakeLock(context);
		wl.acquire();

		AsyncHandler.post(new Runnable() {

			@Override
			public void run() {
				handleBoot(context);
				wl.release();
				result.finish();
			}
		});
	}

	/**
	 * 启动之后执行的操作
	 */
	private void handleBoot(Context context) {
		Intent intent = Watcher.createIntent(context, WatcherService.class);
		Watcher.startService(context, intent);
		Watcher.toast(context, "Service WatcherService start");
	}
}
