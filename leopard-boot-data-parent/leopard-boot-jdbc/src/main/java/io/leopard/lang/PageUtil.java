package io.leopard.lang;

public class PageUtil {

	/**
	 * 根据start和size获取页码
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
	public static int getPageId(int start, int size) {
		return (start / size) + 1;
	}
}
