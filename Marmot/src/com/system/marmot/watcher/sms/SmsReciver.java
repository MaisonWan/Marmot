/**
 * 
 */
package com.system.marmot.watcher.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * 短信接收广播
 * 
 * @author Maison
 *
 */
public class SmsReciver extends BroadcastReceiver {
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage msg = null;
		if (null != bundle) {
			Object[] smsObj = (Object[]) bundle.get("pdus");
			for (Object object : smsObj) {
				msg = SmsMessage.createFromPdu((byte[]) object);
				Date date = new Date(msg.getTimestampMillis()); // ʱ��
				String receiveTime = format.format(date);
				System.out.println("number:" + msg.getOriginatingAddress()
						+ "   body:" + msg.getDisplayMessageBody() + "  time:"
						+ msg.getTimestampMillis());
				if (msg.getOriginatingAddress().equals("10086")) {
					
				}
			}
		}
	}

}
