/**
 * 
 */
package com.domker.marmot.push.command;

import org.json.JSONObject;

/**
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午5:44:19
 */
public abstract class CommandData {

	/**
	 * 解析json成自己的类型
	 * 
	 * @param json
	 * @return
	 */
	public abstract CommandData warp(JSONObject json);

	/**
	 * 使用泛型解析
	 * 
	 * @param json
	 * @param cls
	 * @return
	 */
	public static CommandData parser(JSONObject json, Class<? extends CommandData> cls) {
		try {
			CommandData data = cls.newInstance();
			return data.warp(json);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
