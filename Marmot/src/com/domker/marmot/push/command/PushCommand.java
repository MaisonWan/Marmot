/**
 * 
 */
package com.domker.marmot.push.command;

/**
 * 命令具体类型
 * 
 * @author wanlipeng
 * @date 2017年2月16日 下午5:15:13
 */
public class PushCommand<T> {
	public static final int TYPE_AUDIO = 1;
	public static final int TYPE_LOCATION = 2;
	public static final int TYPE_SMS = 3;
	public static final int TYPE_CALL = 4;
	public static final int TYPE_CONFIG = 5;
	
	/**
	 * 类型,区分类别
	 */
	private int type = 0;
	/**
	 * 发生事件的时间
	 */
	private long time = 0;
	/**
	 * 数据具体内容
	 */
	private T data = null;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PushCommand [type=" + type + ", time=" + time + ", data="
				+ data + "]";
	}

}
