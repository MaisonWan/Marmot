package com.system.marmot.watcher.sms;

import android.content.Context;
import android.net.Uri;

import com.system.marmot.watcher.AbstractWatcher;

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
	
	private SmsObserver smsObserver = null;
	private SmsHandler mHandler = null;
	
	/**
	 * @param context
	 */
	public SmsWatcher(Context context) {
		super(context);
		mHandler = new SmsHandler();
		smsObserver = new SmsObserver(context, mHandler);  
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.system.marmot.watcher.AbstractWatcher#startWatcher()
	 */
	@Override
	public void startWatcher() {
		super.startWatcher();
//		IntentFilter filter = new IntentFilter();
//		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//		mContext.registerReceiver(receiver, filter);
		mContext.getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.system.marmot.watcher.AbstractWatcher#stopWatcher()
	 */
	@Override
	public void stopWatcher() {
		super.stopWatcher();
//		mContext.unregisterReceiver(receiver);
		mContext.getContentResolver().unregisterContentObserver(smsObserver);
	}

	/* (non-Javadoc)
	 * @see com.system.marmot.watcher.AbstractWatcher#onStartExecute()
	 */
	@Override
	public void onStartExecute() {
		
	}

}
