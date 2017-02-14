/**
 * 
 */
package com.domker.marmot.audio;

import com.domker.marmot.core.AsyncHandler;
import com.domker.marmot.log.MLog;

import android.content.Context;

/**
 * 录制管理器
 * 
 * @author wanlipeng
 * @date 2017年2月14日 下午4:51:51
 */
public class RecordManager {
	private Context mContext = null;

	public RecordManager(Context mContext) {
		this.mContext = mContext;
	}
	
	/**
	 * 录制音频
	 */
	public void recordAudio() {
		final RecordTask task = new RecordTask();
		task.execute(0);
		AsyncHandler.post(new Runnable() {
			
			@Override
			public void run() {
				try {
					task.setRecordState(false);
					String path = task.get();
					MLog.i("Record Path = " + path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10000);
	}
}
