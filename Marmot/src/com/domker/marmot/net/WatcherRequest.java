/**
 * 
 */
package com.domker.marmot.net;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonRequest;
import com.domker.marmot.log.MLog;
import com.google.gson.Gson;

/**
 * 使用异步网络请求的库
 * 
 * @author Maison
 *
 */
public class WatcherRequest extends JsonRequest<ResponseResult> {
	private static Gson gson = new Gson();
	private static ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError e) {
			MLog.i("onErrorResponse: " + e.getMessage());
		}
	};

	/**
	 * @param method
	 * @param url
	 * @param requestBody
	 * @param listener
	 * @param errorListener
	 */
	public WatcherRequest(int method, String url, String requestBody,
			Listener<ResponseResult> listener) {
		super(method, url, requestBody, listener, mErrorListener);
	}

	/**
	 * @param url
	 * @param requestBody
	 * @param listener
	 */
	public WatcherRequest(String url, String requestBody,
			Listener<ResponseResult> listener) {
		super(Request.Method.POST, url, requestBody, listener, mErrorListener);
	}

	/**
	 * 工厂方法，创建监视请求
	 * 
	 * @param url
	 * @param requestObj
	 * @param listener
	 * @return
	 */
	public static WatcherRequest create(String url, Object requestObj,
			Listener<ResponseResult> listener) {
		String requestBody = gson.toJson(requestObj);
		MLog.i("RequestBody", requestBody);
		return new WatcherRequest(url, requestBody, listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.android.volley.toolbox.JsonRequest#parseNetworkResponse(com.android
	 * .volley.NetworkResponse)
	 */
	@Override
	protected Response<ResponseResult> parseNetworkResponse(NetworkResponse resp) {
		String data = new String(resp.data);
		ResponseResult result = gson.fromJson(data, ResponseResult.class);
		return Response.success(result, null);
	}

}
