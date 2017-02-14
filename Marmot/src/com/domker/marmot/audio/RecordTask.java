/**
 * 
 */
package com.domker.marmot.audio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import com.domker.marmot.core.FileUtils;
import com.domker.marmot.core.Watcher;
import com.domker.marmot.log.MLog;

/**
 * 
 * @author wanlipeng
 * @date 2017年2月14日 下午4:54:40
 */
public class RecordTask extends AsyncTask<Integer, Void, String> {
	/**
	 * 录音采样率 16000Hz
	 */
	public static final int SAMPLE_RATE = 16000;
	/**
	 * 样本点位数
	 */
	public static final int SAMPLE_BYTES = 2;
	/**
	 * 录音声道 AudioFormat.CHANNEL_IN_MONO 单声道 AudioFormat.CHANNEL_IN_STEREO 双声道
	 */
	public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	/**
	 * 录音制式
	 */
	public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
	
	private static final String AUDIO_PATH = Watcher.getSdcardPath() + "/Marmot/";
	
	private AudioRecord mAudioRecord;
	private int bufferSize;
	private boolean mRecordState = false;
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		FileUtils.makeDirs(AUDIO_PATH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
	 */
	@Override
	protected String doInBackground(Integer... params) {
		initAudioRecord();
		return record();
	}

	private void initAudioRecord() {
		int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
		MLog.i("系统要求最小缓冲区大小为：" + minBufferSize);
		int supposedBufferSize = 3200;
		bufferSize = Math.max(minBufferSize, supposedBufferSize);
		MLog.i("录音缓冲区大小为：" + bufferSize);
		mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_COMMUNICATION, SAMPLE_RATE,
				CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
	}
	
	/**
	 * 
	 * @return
	 */
	private String record() {
		String filePath = getFilePath();
		if (filePath == null) {
			return null;
		}
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			OutputStream os = new FileOutputStream(file);
			byte[] buffer = new byte[bufferSize];
			mAudioRecord.startRecording();
			setRecordState(true);
			while (isRecordState()) {
				int bufferedReadResult = mAudioRecord.read(buffer, 0, bufferSize);
				os.write(buffer, 0, bufferedReadResult);
			}
			mAudioRecord.stop();
			os.close();
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			setRecordState(false);
		}
	}

	/**
	 * @return
	 */
	private boolean isRecordState() {
		return mRecordState;
	}

	/**
	 * 设置当前录音状态
	 * 
	 * @param b
	 */
	public void setRecordState(boolean b) {
		mRecordState = b;
	}

	
	/**
	 * @return
	 */
	private String getFilePath() {
		return AUDIO_PATH + "audio_" + System.currentTimeMillis() + ".pcm";
	}
}
