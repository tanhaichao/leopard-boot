package io.leopard.lang.inum;

public class OnumUtil {

	/**
	 * 在里面
	 * 
	 * @param snum
	 * @param snums
	 * @return
	 */
	public static boolean isWithin(Snum snum, Snum... snums) {
		if (snum == null) {
			throw new IllegalArgumentException("snum不允许为空.");
		}
		if (snums.length == 0) {
			throw new IllegalArgumentException("动态参数个数不能为0.");
		}
		String key = snum.getKey();
		for (Snum snum2 : snums) {
			if (snum2.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 在里面
	 * 
	 * @param inum
	 * @param inums
	 * @return
	 */
	public static boolean isWithin(Inum inum, Inum... inums) {
		if (inum == null) {
			throw new IllegalArgumentException("inum不允许为空.");
		}
		if (inums.length == 0) {
			throw new IllegalArgumentException("动态参数个数不能为0.");
		}
		int key = inum.getKey();
		for (Inum inum2 : inums) {
			if (inum2.getKey() == key) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否不在里面
	 * 
	 * @param inum
	 * @param inums
	 * @return
	 */
	public static boolean isNotWithin(Inum inum, Inum... inums) {
		return !isWithin(inum, inums);
	}

	/**
	 * 是否不在里面
	 * 
	 * @param snum
	 * @param snums
	 * @return
	 */
	public static boolean isNotWithin(Snum snum, Snum... snums) {
		return !isWithin(snum, snums);
	}

}
