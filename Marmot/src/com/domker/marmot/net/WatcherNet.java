/**
 * 
 */
package com.domker.marmot.net;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;

/**
 * 网络请求
 * 
 * @author Maison
 * 
 */
public class WatcherNet {
	private static WatcherNet sIntance = null;

	private RequestQueue mRequestQueue = null;

	private WatcherNet(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public static WatcherNet getInstance() {
		return sIntance;
	}

	public static void init(Context context) {
		sIntance = new WatcherNet(context);
	}
	
	/**
	 * 添加请求到队列
	 * 
	 * @param request
	 */
	public void addRequest(WatcherRequest request) {
		mRequestQueue.add(request);
	}
}
