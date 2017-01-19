/**
 * 
 */
package com.domker.marmot.push;

import android.content.Context;

import com.domker.marmot.log.MLog;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * 推送管理器
 * 
 * @author wanlipeng
 * @date 2017年1月17日 上午11:38:24
 */
public final class PushManager {
	private PushAgent mPushAgent = null;
	private static PushManager mPushManager = null;
	
	public static PushManager getInstance(Context context) {
		if (mPushManager == null) {
			mPushManager = new PushManager(context);
		}
		return mPushManager;
	}

	private PushManager(Context context) {
		mPushAgent = PushAgent.getInstance(context);
		mPushAgent.setDebugMode(false);
		mPushAgent.setPushIntentServiceClass(PushIntentService.class);
	}
	
	public void register() {
		mPushAgent.setPushCheck(true);
		mPushAgent.setCallback(new IUmengCallback() {
			
			@Override
			public void onSuccess() {
				MLog.i("onSuccess: " + mPushAgent.getRegistrationId());
			}
			
			@Override
			public void onFailure(String arg0, String arg1) {
				MLog.i("onFailure: " + arg0 + "-" + arg1);
			}
		});
		
		mPushAgent.register(new IUmengRegisterCallback() {
			
			@Override
			public void onSuccess(String deviceToken) {
				MLog.i("onSuccess: " + deviceToken);
			}
			
			@Override
			public void onFailure(String arg0, String arg1) {
				MLog.i("onFailure: " + arg0 + "-" + arg1);
			}
		});
		for (int i = 0; i < 100; i++) {
			MLog.i(i + " getRegistrationId: " + mPushAgent.getRegistrationId());
		}
	}
}
