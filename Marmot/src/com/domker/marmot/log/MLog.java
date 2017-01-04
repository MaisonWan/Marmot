/**
 * 
 */
package com.domker.marmot.log;

import android.util.Log;

/**
 * 
 * @author wanlipeng
 * @date 2017年1月4日 下午7:34:58
 */
public final class MLog {
	public static final boolean DEBUG = true;
	public static final String TAG = "Marmot";

	public static void i(String msg) {
		if (DEBUG) {
			Log.i(TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(tag, msg);
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
		}
	}
}
