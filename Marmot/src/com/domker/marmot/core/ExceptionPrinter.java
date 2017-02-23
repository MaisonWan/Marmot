package com.domker.marmot.core;

import com.domker.marmot.log.MLog;

/**
 * Exception 打印信息封装类
 * 
 * @author wanlipeng
 * @date 2017年2月23日 下午2:58:18
 */
public class ExceptionPrinter {
	public static final boolean DEBUG = MLog.DEBUG;
	public static final String LOG_TAG_EXCEPTION = "MarmotException";

	public static void printStackTrace(Throwable e) {
		if (DEBUG && e != null) {
			MLog.e(LOG_TAG_EXCEPTION, e.getMessage());
			e.printStackTrace();
		}
	}

}
