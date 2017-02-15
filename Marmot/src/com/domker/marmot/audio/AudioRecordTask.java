/**
 * 
 */
package com.domker.marmot.audio;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.domker.marmot.core.AsynResult;
import com.domker.marmot.core.FileUtils;
import com.domker.marmot.log.MLog;

/**
 * 从系统底层库那到音频流，存储为pcm
 * 
 * @author wanlipeng
 * @date 2017年2月14日 下午4:54:40
 */
public class AudioRecordTask extends AbstractRecordTask<String> {
	/**
	 * 录音采样率 44100Hz
	 */
	public static final int SAMPLE_RATE = 44100;
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
	
	private AudioRecord mAudioRecord;
	private int bufferSize;
	private boolean mRecordState = false;
	
	private void initAudioRecord() {
		int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);
		MLog.i("系统要求最小缓冲区大小为：" + minBufferSize);
		int supposedBufferSize = 3200;
		bufferSize = Math.max(minBufferSize, supposedBufferSize);
		MLog.i("录音缓冲区大小为：" + bufferSize);
		mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
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

	/* (non-Javadoc)
	 * @see com.domker.marmot.audio.AbstractRecordTask#startRecord(com.domker.marmot.core.AsynResult)
	 */
	@Override
	public void startRecord(AsynResult<? super String> result) {
		startRecord(result, 10 * 1000);
	}

	/* (non-Javadoc)
	 * @see com.domker.marmot.audio.AbstractRecordTask#startRecord(com.domker.marmot.core.AsynResult, long)
	 */
	@Override
	public void startRecord(final AsynResult<? super String> result, long milliseconds) {
		final String path = getFilePath();
		Flowable.create(new FlowableOnSubscribe<String>() {

			@Override
			public void subscribe(FlowableEmitter<String> e) throws Exception {
				e.onNext(record());
				e.onComplete();
			}
		}, BackpressureStrategy.BUFFER)
		.observeOn(AndroidSchedulers.mainThread())
		.subscribeOn(Schedulers.io())
		.subscribe(new Subscriber<String>() {

			@Override
			public void onComplete() {
				
			}

			@Override
			public void onError(Throwable e) {
				result.onResult(false, null);
			}

			@Override
			public void onNext(String n) {
				result.onResult(true, path);
			}

			@Override
			public void onSubscribe(Subscription sub) {
				initAudioRecord();
				FileUtils.makeDirs(AUDIO_PATH);
				sub.request(Long.MAX_VALUE);
			}
		});
		Flowable.timer(milliseconds, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {

			@Override
			public void accept(Long t) throws Exception {
				setRecordState(false);
			}
		});
	}

	/* (non-Javadoc)
	 * @see com.domker.marmot.audio.AbstractRecordTask#stopRecord()
	 */
	@Override
	public void stopRecord() {
		setRecordState(false);
	}
}
