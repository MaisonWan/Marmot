/**
 * 
 */
package com.domker.marmot;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
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
	private ActivityManager am = null;
	private String processName = null;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {
		if (isServiceProcessAlive(context)) {
			// 如果进程存在，则返回
			return;
		}
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
	 * 判断服务进程是否存在
	 * 
	 * @param context
	 * @return
	 */
	private boolean isServiceProcessAlive(Context context) {
		if (am == null) {
			am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
			processName = context.getString(R.string.watcher_process);
		}
		for (RunningAppProcessInfo p : am.getRunningAppProcesses()) {
			if (p.processName.equals(processName)) {
				return true;
			}
		}
		return false;
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
