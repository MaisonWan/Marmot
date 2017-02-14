/**
 * 
 */
package com.domker.marmot.push;

import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.WindowManager;

import com.domker.marmot.log.MLog;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

/**
 * 推送服务
 * 
 * @author wanlipeng
 * @date 2017年1月17日 下午2:19:29
 */
public class PushIntentService extends UmengMessageService {

	/* (non-Javadoc)
	 * @see com.umeng.message.UmengMessageService#onMessage(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onMessage(Context context, Intent intent) {
		String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        UMessage msg;
		try {
			//message:{"msg_id":"us56282148705927082310","random_min":0,"display_type":"custom","body":{"custom":"情人节"}}
			msg = new UMessage(new JSONObject(message));
			showSystemAlertDialog(msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showSystemAlertDialog(final UMessage msg) {
		MLog.i("title: " + msg.title + " text: " + msg.text + " custom: " + msg.custom);
		Builder builder = new Builder(getApplicationContext());
		builder.setTitle(msg.title);
		builder.setMessage(msg.text);
		builder.setNegativeButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
			}
		});
		Dialog dialog = builder.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}
}
