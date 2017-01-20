/**
 * 
 */
package com.domker.marmot.push;


import android.content.Context;

import com.domker.marmot.log.MLog;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * 推送管理器
 * 
 * @author wanlipeng
 * @date 2017年1月17日 上午11:38:24
 */
public final class PushManager {
	public static final int NOTIFICATION_PLAY_SDK_ENABLE = 1;
	
	private Context mContext = null;
	private PushAgent mPushAgent = null;
	private static PushManager mPushManager = null;
	
	public static PushManager getInstance(Context context) {
		if (mPushManager == null) {
			mPushManager = new PushManager(context);
		}
		return mPushManager;
	}

	private PushManager(Context context) {
		mContext = context;
		mPushAgent = PushAgent.getInstance(context);
		mPushAgent.setDebugMode(true);
		register();
	}
	
	/**
	 * 注册申请服务器，返回deviceToken
	 * 
	 * @return
	 */
	public PushManager register() {
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
		return this;
	}
	
	/**
	 * 自定义push服务接管接收推送消息
	 * 
	 * @return
	 */
	public PushManager pushService() {
		mPushAgent.setPushIntentServiceClass(PushIntentService.class);
		return this;
	}
}
