/**
 * 
 */
package com.domker.marmot.core;

import com.domker.marmot.log.MLog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

/**
 * 核心生成类
 * 
 * @author wanlipeng
 * @date 2017年1月5日 下午6:05:29
 */
public final class Watcher {
	private static Context sContext = null;

	/**
	 * 传递Application的Context进行初始化
	 * 
	 * @param applicationContext
	 */
	public static void initContext(Context applicationContext) {
		sContext = applicationContext;
	}
	
	/**
	 * 得到Application的Context
	 * 
	 * @return
	 */
	public static Context getApplicationContext() {
		return sContext;
	}
	
	/**
	 * 创建Intent
	 * 
	 * @param context
	 * @param cls
	 * @return
	 */
	public static Intent createIntent(Context context, Class<?> cls) {
		return new Intent(context, cls);
	}
	
	/**
	 * 启动服务
	 * 
	 * @param context
	 * @param intent
	 * @return
	 */
	public static ComponentName startService(Context context, Intent intent) {
		MLog.i("startService: " + intent.getComponent().getClassName());
		return context.startService(intent);
	}
	
	/**
	 * 显示Toast方式的信息
	 * 
	 * @param context
	 * @param message
	 */
	public static void toast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 获取Sd卡路径，如果不存在，则返回程序目录
	 * 
	 * @return
	 */
	public static String getSdcardPath() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return Environment.getExternalStorageDirectory().toString();
		}
		if (sContext != null) {
			return sContext.getFilesDir().getPath();
		}
		return null;
	}
}
