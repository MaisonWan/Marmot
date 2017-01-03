/**
 * 
 */
package com.domker.marmot.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

/**
 * 网络信息
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午9:11:01
 */
public final class NetInfo {
	
	/**
	 * 获取外网IP等信息
	 * 
	 * @return
	 */
	public static String getNetInfo() {
		String IP = "";
		try {
			String address = "http://ip.taobao.com/service/getIpInfo.php?ip=myip";
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();
				// 将流转化为字符串
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String tmpString = "";
				StringBuilder retJSON = new StringBuilder();
				while ((tmpString = reader.readLine()) != null) {
					retJSON.append(tmpString);
				}

				JSONObject jsonObject = new JSONObject(retJSON.toString());
				String code = jsonObject.getString("code");
				if (code.equals("0")) {
					JSONObject data = jsonObject.getJSONObject("data");
					String ip = data.getString("ip");
					String country = data.getString("country");
					String area = data.getString("area") + "区";
					String region = data.getString("region");
					String city = data.getString("city");
					String isp = data.getString("isp");
					IP = String.format("ip=%s\ncountry=%s\narea=%s\nregion=%s\ncity=%s\nisp=%s", ip, country, area, region, city, isp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			IP = "Error";
		}
		return IP;
	}
}
