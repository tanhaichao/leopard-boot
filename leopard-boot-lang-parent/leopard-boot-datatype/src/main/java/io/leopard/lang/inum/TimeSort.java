package io.leopard.lang.inum;

/**
 * 时间排序
 * 
 * @author 谭海潮
 *
 */
public enum TimeSort implements Sort {
	POSTTIME("posttime", "按发表时间排序"), LMODIFY("lmodify", "按更新时间排序");

	private String key;

	private String desc;

	private TimeSort(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}
