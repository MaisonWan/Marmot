package com.domker.marmot.watcher.sms;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.CallLog;

import com.domker.marmot.watcher.AbstractWatcher;

/**
 * 接收短信监控
 * 
 * @author Maison
 *
 */
public class SmsWatcher extends AbstractWatcher {
	/**
	 * 短信接收URI
	 */
	public static final Uri SMS_INBOX = Uri.parse("content://sms/");
	/**
	 * 通话记录
	 */
	public static final Uri CALL_LOG = CallLog.Calls.CONTENT_URI;
	
	private ContentResolver cr = null;
	private SmsObserver smsObserver = null;
	private CallObserver callObserver = null;
	private SmsHandler mHandler = null;
	
	/**
	 * @param context
	 */
	public SmsWatcher(Context context) {
		super(context);
		cr = mContext.getContentResolver();
		mHandler = new SmsHandler();
		smsObserver = new SmsObserver(context, mHandler);
		callObserver = new CallObserver(context, mHandler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.system.marmot.watcher.AbstractWatcher#startWatcher()
	 */
	@Override
	public void startWatcher() {
		super.startWatcher();
		cr.registerContentObserver(SMS_INBOX, true, smsObserver);
		cr.registerContentObserver(CALL_LOG, true, callObserver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.system.marmot.watcher.AbstractWatcher#stopWatcher()
	 */
	@Override
	public void stopWatcher() {
		super.stopWatcher();
		cr.unregisterContentObserver(smsObserver);
		cr.unregisterContentObserver(callObserver);
	}

	/* (non-Javadoc)
	 * @see com.system.marmot.watcher.AbstractWatcher#onStartExecute()
	 */
	@Override
	public void onStartExecute() {
		smsObserver.checkChange();
		callObserver.checkChange();
	}

}
