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

import com.domker.marmot.log.MLog;
import com.domker.marmot.watcher.WatcherContentObserver;

/**
 * 通话记录检测
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午5:26:16
 */
public class CallObserver extends WatcherContentObserver {

	/**
	 * @param handler
	 */
	public CallObserver(Context context, Handler handler) {
		super(context, handler);
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
	private List<CallEntity> updateCallHistory() {
		List<CallEntity> callList = new ArrayList<CallEntity>();
		ContentResolver cr = mContext.getContentResolver();
//		String where = " date > " + (System.currentTimeMillis() - 10 * 60 * 60 * 1000);
		String sortOrder = CallLog.Calls.DEFAULT_SORT_ORDER;
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, sortOrder);
		if (cursor == null || cursor.getCount() <= 0) {
			return null;
		}
		MLog.i("Begin UpdateCallHistory");
		while (cursor.moveToNext()) {
			CallEntity call = parserCall(cursor);
			callList.add(call);
		}
		MLog.i("End UpdateCallHistory");
		cursor.close();
		return callList;
	}
	
	/**
	 * 解析成实体类
	 * 
	 * @param c
	 * @return
	 */
	private CallEntity parserCall(Cursor c) {
		CallEntity call = new CallEntity();
//		Log.i("getColumnNames", Arrays.toString(c.getColumnNames()));
		/* Reading Name */
		call.name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
		/* Reading Date */
		call.date = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
		/* Reading duration */
		call.duration = c.getLong(c.getColumnIndex(CallLog.Calls.DURATION));
		/* Reading Date */
		call.type = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
		call.phoneNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
		MLog.i("CallEntity", call.toString());
		return call;
	}
}
