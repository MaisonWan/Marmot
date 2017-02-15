/**
 * 
 */
package com.domker.marmot.core;

/**
 * 异步结果
 * 
 * @author wanlipeng
 * @date 2017年2月15日 下午5:53:56
 */
public interface AsynResult<T> {
	/**
	 * 返回异步结果
	 * 
	 * @param succeed 成功返回true，否则返回false
	 * @param t 传递类型数据
	 */
	void onResult(boolean succeed, T t);
}
