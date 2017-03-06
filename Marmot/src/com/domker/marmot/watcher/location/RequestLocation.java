/**
 * 
 */
package com.domker.marmot.watcher.location;

import com.amap.api.location.AMapLocation;

/**
 * @author Maison
 *
 */
public class RequestLocation {
	private int type = 0;
	private double longitude;
	private double latitude;
	private float accuracy;
	private String provider;
	private float speed;
	private float bearing;
	private int satellites;
	private String country;
	private String province;
	private String city;
	private String cityCode;
	private String district;
	private String adCode;
	private String address;
	private String poiName;
	private long time;
	private String netType;

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getBearing() {
		return bearing;
	}

	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	public int getSatellites() {
		return satellites;
	}

	public void setSatellites(int satellites) {
		this.satellites = satellites;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPoiName() {
		return poiName;
	}

	public void setPoiName(String poiName) {
		this.poiName = poiName;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public static RequestLocation convert(AMapLocation location) {
		RequestLocation l = new RequestLocation();
		l.type = location.getLocationType();
		l.longitude = location.getLongitude();
		l.latitude = location.getLatitude();
		l.accuracy = location.getAccuracy();
		l.provider = location.getProvider();
		l.speed = location.getSpeed();
		l.bearing = location.getBearing();
		l.satellites = location.getSatellites();
		l.country = location.getCountry();
		l.province = location.getProvince();
		l.city = location.getCity();
		l.cityCode = location.getCityCode();
		l.district = location.getDistrict();
		l.adCode = location.getAdCode();
		l.address = location.getAddress();
		l.poiName = location.getPoiName();
		l.time = location.getTime();
		return l;
	}
}
