package com.domker.marmot.core;

import android.content.Context;
import android.os.PowerManager;

/**
 * 锁定CPU，不允许休眠
 * 
 * @author wanlipeng
 * @date 2017年1月5日 下午5:54:03
 */
public class CpuWakeLock {

	private static PowerManager.WakeLock sCpuWakeLock;

	public static PowerManager.WakeLock createPartialWakeLock(Context context) {
		PowerManager pw = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		return pw.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WakeLock");
	}

	/**
	 * 获取CPU，防止休眠
	 * 
	 * @param context
	 */
	public static void acquireCpuWakeLock(Context context) {
		if (sCpuWakeLock != null) {
			return;
		}
		sCpuWakeLock = createPartialWakeLock(context);
		sCpuWakeLock.acquire();
	}

	/**
	 * 释放CPU锁，可以休眠
	 */
	public static void releaseCpuLock() {
		if (sCpuWakeLock != null) {
			sCpuWakeLock.release();
			sCpuWakeLock = null;
		}
	}

}
