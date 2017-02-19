/**
 * 
 */
package com.domker.marmot.watcher.sms;

/**
 * 通话信息
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午5:20:58
 */
public class CallEntity {
	public String uid;
	/**
	 * 联系人名字
	 */
	public String name;
	/**
	 * 电话号码
	 */
	public String phoneNumber;
	/**
	 * 通话时长，单位秒
	 */
	public long duration;
	/**
	 * 类型, 1是incoming, 2是outgoing，3是missed
	 */
	public int type;
	/**
	 * 触发时间
	 */
	public long date;

	@Override
	public String toString() {
		return "[name=" + name + ", phoneNumber=" + phoneNumber + ", duration="
				+ duration + ", type=" + type + ", date=" + date + "]";
	}

}
