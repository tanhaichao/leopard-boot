package io.leopard.boot.qrcode;

import java.io.File;

/**
 * Logo信息
 * 
 * @author 谭海潮
 *
 */
public class Logo {

	/**
	 * 文件
	 */
	File file;

	/**
	 * 是否压缩
	 */
	boolean needCompress;

	int width = 60;

	int height = 60;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public boolean isNeedCompress() {
		return needCompress;
	}

	public void setNeedCompress(boolean needCompress) {
		this.needCompress = needCompress;
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
