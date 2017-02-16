/**
 * 
 */
package com.domker.marmot.push.command;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * push过来的消息，进行命令解析。
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午5:07:18
 */
public class CommandParser {
	
	/**
	 * 解析命令
	 * 
	 * @param cmd 服务端push过来的json文本
	 * @return
	 */
	public static PushCommand<? extends CommandData> parser(String json) {
		try {
			JSONObject jobject = new JSONObject(json);
			int type = jobject.getInt("type");
			long time = jobject.getInt("time");
			JSONObject data = jobject.getJSONObject("data");
			PushCommand<CommandData> cmd = new PushCommand<CommandData>();
			cmd.setType(type);
			cmd.setTime(time);
			switch (type) {
			case PushCommand.TYPE_AUDIO:
				cmd.setData(CommandData.parser(data, CommandAudio.class));
				break;
			case PushCommand.TYPE_CALL:
				cmd.setData(CommandData.parser(data, CommandCall.class));
				break;
			case PushCommand.TYPE_CONFIG:
				cmd.setData(CommandData.parser(data, CommandConfig.class));
				break;
			case PushCommand.TYPE_LOCATION:
				cmd.setData(CommandData.parser(data, CommandLocation.class));
				break;
			case PushCommand.TYPE_SMS:
				cmd.setData(CommandData.parser(data, CommandSms.class));
				break;
			}
			return cmd;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
