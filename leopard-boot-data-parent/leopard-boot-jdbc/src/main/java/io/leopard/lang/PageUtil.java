package io.leopard.lang;

/**
 * 分页工具类
 * 
 * @author 谭海潮
 *
 */
public class PageUtil {

	/**
	 * 根据分页ID获取记录起始位置
	 * 
	 * @param pageId
	 * @param size
	 * @return
	 */
	public static int getStart(int pageId, int size) {
		if (pageId < 1) {
			throw new IllegalArgumentException("pageid不能小于1.");
		}
		if (size < 1) {
			throw new IllegalArgumentException("size不能小于1.");
		}
		return (pageId - 1) * size;
	}

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
