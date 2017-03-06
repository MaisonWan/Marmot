/**
 * 
 */
package com.domker.marmot.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

import com.domker.marmot.core.Watcher;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.Flattener;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.ChangelessFileNameGenerator;

/**
 * Log输出
 * 
 * @author wanlipeng
 * @date 2017年1月4日 下午7:34:58
 */
public final class MLog {
	public static final boolean DEBUG = true;
	public static final String TAG = "Marmot";

	static {
		LogConfiguration config = new LogConfiguration.Builder().tag(TAG).build();
		String sdCard = Watcher.getSdcardPath();
		Printer filePrinter = new FilePrinter.Builder(sdCard + "/Marmot/Log/")
				.logFlattener(new FileLogFlattener())
				.fileNameGenerator(new ChangelessFileNameGenerator("marmot.log")).build();
		XLog.init(config, filePrinter);
	}

	public static void i(String msg) {
		if (DEBUG) {
			Log.i(TAG, msg);
			XLog.i(msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			Log.i(tag, msg);
			XLog.i(msg);
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			Log.e(TAG, msg);
			XLog.e(msg);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			Log.e(tag, msg);
			XLog.e(msg);
		}
	}

	public static void e(String tag, Throwable e) {
		if (DEBUG) {
			Log.e(tag, "error", e);
			XLog.e(tag, e);
		}
	}

	private static class FileLogFlattener implements Flattener {
		private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.elvishew.xlog.flattener.Flattener#flatten(int,
		 * java.lang.String, java.lang.String)
		 */
		@Override
		public CharSequence flatten(int logLevel, String tag, String message) {
			return format.format(new Date()) + '|'
					+ LogLevel.getShortLevelName(logLevel) + '|' + tag + '|'
					+ message;
		}

	}
}
