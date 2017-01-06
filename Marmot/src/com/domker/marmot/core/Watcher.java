/**
 * 
 */
package com.domker.marmot.core;

import com.domker.marmot.log.MLog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 核心生成类
 * 
 * @author wanlipeng
 * @date 2017年1月5日 下午6:05:29
 */
public final class Watcher {
	
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
}
