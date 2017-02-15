/**
 * 
 */
package com.domker.marmot.audio;

import com.domker.marmot.core.AsynResult;
import com.domker.marmot.core.Watcher;

/**
 * 录制任务基类
 * 
 * @author wanlipeng
 * @date 2017年2月15日 下午6:05:10
 */
public abstract class AbstractRecordTask<T> {
	protected static final String AUDIO_PATH = Watcher.getSdcardPath() + "/Marmot/";

	/**
	 * 开始录制，默认录制十秒钟
	 * 
	 * @param result 异步结果回调
	 */
	public abstract void startRecord(final AsynResult<? super T> result);

	/**
	 * 开始录制，指定时间后结束
	 * 
	 * @param result 异步结果回调
	 * @param milliseconds 时长
	 */
	public abstract void startRecord(final AsynResult<? super T> result,
			long milliseconds);
	
	/**
	 * 停止录音
	 */
	public abstract void stopRecord();
}
