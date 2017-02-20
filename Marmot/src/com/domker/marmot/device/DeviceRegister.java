/**
 * 
 */
package com.domker.marmot.device;

/**
 * 注册设备信息
 * 
 * @author Maison
 *
 */
public class DeviceRegister {
	private String uid;
	private String imei;
	private String imsi;
	private String phoneNumber;
	private String serialNumber;
	private int screenWidth;
	private int screenHeight;
	private float density;
	private int densityDpi;
	private int sdkVersion;

	// os build
	private String cpuABI;
	private String model;
	private String versionRelease;
	private String brand;
	private String board;
	private String manufacturer;
	private String user;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getDensityDpi() {
		return densityDpi;
	}

	public void setDensityDpi(int densityDpi) {
		this.densityDpi = densityDpi;
	}

	public int getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(int sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getCpuABI() {
		return cpuABI;
	}

	public void setCpuABI(String cpuABI) {
		this.cpuABI = cpuABI;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersionRelease() {
		return versionRelease;
	}

	public void setVersionRelease(String versionRelease) {
		this.versionRelease = versionRelease;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
