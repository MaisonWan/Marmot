/**
 * 
 */
package com.domker.marmot.push.command;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午6:02:08
 */
public class CommandSms extends CommandData {
	private long beforeTime = 0;
	private long afterTime = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.domker.marmot.push.command.CommandData#warp(org.json.JSONObject)
	 */
	@Override
	public CommandData warp(JSONObject json) {
		try {
			if (json.has("before")) {
				beforeTime = json.getLong("before");
			}
			if (json.has("after")) {
				afterTime = json.getLong("after");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public long getBeforeTime() {
		return beforeTime;
	}

	public void setBeforeTime(long beforeTime) {
		this.beforeTime = beforeTime;
	}

	public long getAfterTime() {
		return afterTime;
	}

	public void setAfterTime(long afterTime) {
		this.afterTime = afterTime;
	}

	@Override
	public String toString() {
		return "CommandSms [beforeTime=" + beforeTime + ", afterTime="
				+ afterTime + "]";
	}

}
