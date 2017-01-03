/**
 * 
 */
package com.domker.marmot.device;

/**
 * 尺寸大小
 * 
 * @author wanlipeng
 * @date 2017年1月3日 下午8:19:44
 */
public class Size {
	private int width = 0;
	private int height = 0;

	public Size() {
	}

	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
