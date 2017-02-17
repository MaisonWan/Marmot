/**
 * 
 */
package com.domker.marmot.push.command;

import org.json.JSONObject;

/**
 * 配置命令
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午6:02:34
 */
public class CommandConfig extends CommandData {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.domker.marmot.push.command.CommandData#warp(org.json.JSONObject)
	 */
	@Override
	public CommandData warp(JSONObject json) {
		return this;
	}

}
