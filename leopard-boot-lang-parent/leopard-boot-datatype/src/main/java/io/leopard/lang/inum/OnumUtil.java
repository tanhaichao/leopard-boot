package io.leopard.lang.inum;

public class OnumUtil {

	/**
	 * 在里面
	 * 
	 * @param onum0
	 * @param onum1
	 * @param onums
	 * @return
	 */
	public static <K> boolean isWithin(Onum<K, ?> onum0, Onum<K, ?> onum1, @SuppressWarnings("unchecked") Onum<K, ?>... onums) {
		if (onum0 == null) {
			throw new IllegalArgumentException("onum0不允许为空.");
		}
		if (onum1 == null) {
			throw new IllegalArgumentException("onum1不允许为空.");
		}
		String key = onum0.getKey().toString();
		if (onum1.getKey().toString().equals(key)) {
			return true;
		}
		for (Onum<K, ?> onum2 : onums) {
			if (onum2.getKey().toString().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否不在里面
	 * 
	 * @param onum0
	 * @param onum1
	 * @param onums
	 * @return
	 */
	public static <K> boolean isNotWithin(Onum<K, ?> onum0, Onum<K, ?> onum1, @SuppressWarnings("unchecked") Onum<K, ?>... onums) {
		return !isWithin(onum0, onum1, onums);
	}

}
