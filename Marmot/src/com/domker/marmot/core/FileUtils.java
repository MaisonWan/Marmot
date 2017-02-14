/**
 * 
 */
package com.domker.marmot.core;

import java.io.File;

import android.text.TextUtils;

/**
 * 
 * @author wanlipeng
 * @date 2017年2月14日 下午5:50:27
 */
public final class FileUtils {
	
	/**
	 * 创建路径
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean makeDirs(String dir) {
		if (TextUtils.isEmpty(dir)) {
			return false;
		}
		File f = new File(dir);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}
	
	/**
	 * 是否存在路径
	 * 
	 * @param path
	 * @return
	 */
	public static boolean exists(String path) {
		File f = new File(path);
		return f.exists();
	}
}
