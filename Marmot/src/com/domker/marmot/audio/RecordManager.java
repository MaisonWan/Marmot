/**
 * 
 */
package com.domker.marmot.audio;

import android.content.Context;

import com.domker.marmot.core.AsynResult;
import com.domker.marmot.log.MLog;

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
		final AbstractRecordTask<String> task = new MediaRecordTask();
		task.startRecord(new AsynResult<String>() {

			@Override
			public void onResult(boolean succeed, String t) {
				MLog.i("Record succeed = " + succeed + " Path = " + t);
			}
		});
//		MediaRecordTask task = new MediaRecordTask();
//		task.startRecord();
	}
}
