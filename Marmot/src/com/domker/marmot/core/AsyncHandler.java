package com.domker.marmot.core;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * 异步任务调度的HandleThread
 * 
 * @author wanlipeng
 * @date 2017年1月5日 下午5:50:58
 */
public class AsyncHandler {

	private static final HandlerThread sHandlerThread = new HandlerThread("AsyncHandler");
	private static final Handler sHandler;

	static {
		sHandlerThread.start();
		sHandler = new Handler(sHandlerThread.getLooper());
	}

	/**
	 * 执行任务
	 * 
	 * @param r
	 */
	public static void post(Runnable r) {
		sHandler.post(r);
	}
	
	public static void post(Runnable r, long delayMillis) {
		sHandler.postDelayed(r, delayMillis);
	}

	private AsyncHandler() {
	}
}
