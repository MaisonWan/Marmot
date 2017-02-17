/**
 * 
 */
package com.domker.marmot.push.command;

import org.json.JSONObject;

/**
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午5:38:05
 */
public class CommandAudio extends CommandData {
	
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
