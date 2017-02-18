/**
 * 
 */
package com.domker.marmot.watcher.location;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.domker.marmot.config.ConfigManager;
import com.domker.marmot.log.MLog;
import com.domker.marmot.net.ResponseResult;
import com.domker.marmot.net.Urls;
import com.domker.marmot.net.WatcherRequest;

/**
 * @author Maison
 *
 */
public class LocationHandler extends Handler implements AMapLocationListener {
	/**
	 * 默认间隔时间
	 */
	public static final int DEFAULT_DELAY_TIME = 15 * 60 * 1000;
	/**
	 * 更新位置
	 */
	public static final int ACTION_UPDATE_LOCATION = 1;

	private Context mContext = null;
	private PowerManager mPowerManager = null;
	private AMapLocationClient mlocationClient = null;

	public LocationHandler(Context context) {
		super();
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mPowerManager = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		mlocationClient = new AMapLocationClient(context);
		// 设置定位监听
		mlocationClient.setLocationListener(this);
		// 设置定位参数
		// mlocationClient.setLocationOption(getDefaultOption());
	}

	/**
	 * 默认的定位参数
	 */
	private AMapLocationClientOption getDefaultOption() {
		AMapLocationClientOption mOption = new AMapLocationClientOption();
		if (mPowerManager.isScreenOn()) {
			// 可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
			mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		} else {
			mOption.setLocationMode(AMapLocationMode.Battery_Saving);
		}
		mOption.setGpsFirst(false);// 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
		mOption.setHttpTimeOut(30000);// 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
		mOption.setInterval(2000);// 可选，设置定位间隔。默认为2秒
		mOption.setNeedAddress(true);// 可选，设置是否返回逆地理地址信息。默认是true
		mOption.setOnceLocation(true);// 可选，设置是否单次定位。默认是false
		mOption.setOnceLocationLatest(true);// 可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
		AMapLocationClientOption
				.setLocationProtocol(AMapLocationProtocol.HTTPS);// 可选，
																	// 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
		mOption.setSensorEnable(true);// 可选，设置是否使用传感器。默认是false
		mOption.setWifiScan(true); // 可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
		mOption.setLocationCacheEnable(true); // 可选，设置是否使用缓存定位，默认为true
		return mOption;
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case ACTION_UPDATE_LOCATION:
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用onDestroy()方法
			// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
			mlocationClient.setLocationOption(getDefaultOption());
			// 启动定位
			mlocationClient.startLocation();
			Log.i("Location", "startLocation");
			sendEmptyMessageDelayed(ACTION_UPDATE_LOCATION, DEFAULT_DELAY_TIME);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.amap.api.location.AMapLocationListener#onLocationChanged(com.amap
	 * .api.location.AMapLocation)
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation == null) {
			return;
		}
		if (amapLocation.getErrorCode() == 0) {
			// 定位成功回调信息，设置相关消息
			postRequest(amapLocation);
		} else {
			Log.e("Location",
					"location Error, ErrCode:" + amapLocation.getErrorCode()
							+ ", errInfo:" + amapLocation.getErrorInfo());
		}
	}

	private void postRequest(AMapLocation amapLocation) {
		RequestLocation location = RequestLocation.convert(amapLocation);
		location.setUid(ConfigManager.getInstance().getUid());
		
		RequestQueue queue = Volley.newRequestQueue(mContext);
		Listener<ResponseResult> listener = new Listener<ResponseResult>() {

			@Override
			public void onResponse(ResponseResult context) {
				MLog.i("onResponse", context.toString());
			}
		};
		WatcherRequest request = WatcherRequest.create(Urls.LOCATION_REGISTER, location, listener);
		queue.add(request);
	}

}
