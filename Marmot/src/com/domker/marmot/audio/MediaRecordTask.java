/**
 * 
 */
package com.domker.marmot.audio;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import android.media.MediaRecorder;

import com.domker.marmot.core.AsynResult;
import com.domker.marmot.core.FileUtils;
import com.domker.marmot.core.Watcher;
import com.domker.marmot.log.MLog;

/**
 * 直接使用MediaRecorder实现录制amr
 * 
 * @author wanlipeng
 * @date 2017年2月15日 上午10:33:43
 */
public class MediaRecordTask {
	/**
	 * 录音采样率 44100Hz
	 */
	public static final int SAMPLE_RATE = 44100;
	
	private MediaRecorder recorder = null;
	private static final String AUDIO_PATH = Watcher.getSdcardPath() + "/Marmot/";
	
	public MediaRecordTask() {
		init();
	}
	
	private void init() {
		FileUtils.makeDirs(AUDIO_PATH);
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//		recorder.setAudioSamplingRate(SAMPLE_RATE);
//		recorder.setAudioChannels(1);
//		recorder.setAudioEncodingBitRate(16);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	}
	
	/**
	 * 录制声音，默认十秒钟
	 * 
	 * @param result
	 */
	public void startRecord(final AsynResult<? super String> result) {
		startRecord(result, 10 * 1000);
	}
	
	/**
	 * 开始录制
	 * 
	 * @param result
	 */
	public void startRecord(final AsynResult<? super String> result, long milliseconds) {
		final String path = startTask();
		if (path == null) {
			result.onResult(false, path);
		}
		Flowable.timer(milliseconds, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {

			@Override
			public void accept(Long t) throws Exception {
				if (t == 0) {
					stopRecord();
					result.onResult(true, path);
				}
			}
		});
	}
	
	private String startTask() {
		String path = getFilePath();
		MLog.i("StartTask Audio Path = " + path);
		recorder.setOutputFile(path);
		try {
			recorder.prepare();
			recorder.start();			
			return path;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 停止录音
	 */
	public void stopRecord() {
		recorder.stop();
		recorder.release();
		MLog.i("StopTask Audio Path");
	}
	
	/**
	 * @return
	 */
	private String getFilePath() {
		return AUDIO_PATH + "audio_" + System.currentTimeMillis() + ".amr";
	}
}
