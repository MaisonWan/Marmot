/**
 * 
 */
package com.domker.marmot.watcher.sms;

import java.util.Arrays;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

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
		String where = " date > " + (System.currentTimeMillis() - 10 * 60 * 60 * 1000);
		Cursor cur = cr.query(SmsWatcher.SMS_INBOX, null, where, null, "date desc");
		if (cur == null) {
			return;
		}
		while (cur.moveToNext()) {
			SmsEntity sms = parserSms(cur);
			// 这里我是要获取自己短信服务号码中的验证码~~
//			Pattern pattern = Pattern.compile(" [a-zA-Z0-9]{10}");
//			Matcher matcher = pattern.matcher(sms.body);
//			if (matcher.find()) {
//				String res = matcher.group().substring(1, 11);
////				mobileText.setText(res);
//			}
			// 处理业务
			if (sms != null) {
				Log.i("sms", sms.toString());
			}
		}
		cur.close();
	}
	
	/**
	 * 读取短信内容
	 * 
	 * @param c
	 */
	private SmsEntity parserSms(Cursor c) {
		try {
			Log.i("getColumnNames", Arrays.toString(c.getColumnNames()));
			SmsEntity s = new SmsEntity();
			s.number = c.getString(c.getColumnIndex("address"));// 发件人地址
			s.name = c.getString(c.getColumnIndex("person"));   // 发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
			s.body = c.getString(c.getColumnIndex("body"));
			s.date = c.getString(c.getColumnIndex("date"));     // date：日期，long型，如1346988516，可以对日期显示格式进行设置
			s.protocol = c.getString(c.getColumnIndex("protocol")); // protocol：协议0 SMS_RPOTO短信，1 MMS_PROTO彩信
			s.read = c.getString(c.getColumnIndex("read")); // 是否阅读0未读，1已读
			s.type = c.getString(c.getColumnIndex("type")); // type：短信类型1是接收到的，2是已发出
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
}
